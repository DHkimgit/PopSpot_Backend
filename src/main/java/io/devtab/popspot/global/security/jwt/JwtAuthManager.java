package io.devtab.popspot.global.security.jwt;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import io.devtab.popspot.domain.user.model.User;
import io.devtab.popspot.global.security.jwt.access.AccessTokenClaim;
import io.devtab.popspot.global.security.jwt.access.AccessTokenStrategy;
import io.devtab.popspot.global.security.jwt.dto.JwtTokens;
import io.devtab.popspot.global.security.jwt.dto.TokenRefreshResponse;
import io.devtab.popspot.global.security.jwt.error.JwtErrorCode;
import io.devtab.popspot.global.security.jwt.exception.JwtErrorException;
import io.devtab.popspot.global.security.jwt.forbidden.ForbiddenTokenService;
import io.devtab.popspot.global.security.jwt.model.JwtClaims;
import io.devtab.popspot.global.security.jwt.refresh.RefreshTokenClaim;
import io.devtab.popspot.global.security.jwt.refresh.RefreshTokenClaimKeys;
import io.devtab.popspot.global.security.jwt.refresh.RefreshTokenEntity;
import io.devtab.popspot.global.security.jwt.refresh.RefreshTokenService;
import io.devtab.popspot.global.security.jwt.refresh.RefreshTokenStrategy;
import io.devtab.popspot.global.security.jwt.util.JwtClaimsParserUtil;

@Component
public class JwtAuthManager {

    private final JwtProvider accessTokenProvider;
    private final JwtProvider refreshTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final ForbiddenTokenService forbiddenTokenService;

    public JwtAuthManager(
        @AccessTokenStrategy JwtProvider accessTokenProvider,
        @RefreshTokenStrategy JwtProvider refreshTokenProvider,
        RefreshTokenService refreshTokenService,
        ForbiddenTokenService forbiddenTokenService) {
        this.accessTokenProvider = accessTokenProvider;
        this.refreshTokenProvider = refreshTokenProvider;
        this.refreshTokenService = refreshTokenService;
        this.forbiddenTokenService = forbiddenTokenService;
    }

    public JwtTokens createToken(User user) {
        String accessToken = accessTokenProvider.generateToken(AccessTokenClaim.of(user.getId(), user.getUserType().name()));
        String refreshToken = refreshTokenProvider.generateToken(RefreshTokenClaim.of(user.getId(), user.getUserType().name()));

        refreshTokenService.save(RefreshTokenEntity.of(user.getId(), refreshToken, toSeconds(refreshTokenProvider.getExpiryDate(refreshToken))));
        return JwtTokens.of(accessToken, refreshToken);
    }

    public TokenRefreshResponse refresh(String refreshToken) {
        JwtClaims claims = refreshTokenProvider.getJwtClaimsFromToken(refreshToken);

        Integer userId = JwtClaimsParserUtil.getClaimsValue(claims, RefreshTokenClaimKeys.USER_ID.getValue(), Integer::parseInt);
        String role = JwtClaimsParserUtil.getClaimsValue(claims, RefreshTokenClaimKeys.ROLE.getValue(), String.class);

        RefreshTokenEntity newRefreshToken;
        try {
            newRefreshToken = refreshTokenService.refresh(userId, refreshToken, refreshTokenProvider.generateToken(RefreshTokenClaim.of(userId, role)));
        } catch (IllegalArgumentException e) {
            throw new JwtErrorException(JwtErrorCode.EXPIRED_TOKEN);
        } catch (IllegalStateException e) {
            throw new JwtErrorException(JwtErrorCode.TAKEN_AWAY_TOKEN);
        }

        String newAccessToken = accessTokenProvider.generateToken(AccessTokenClaim.of(userId, role));

        return TokenRefreshResponse.of(JwtTokens.of(newAccessToken, newRefreshToken.getToken()), userId);
    }

    public void removeAccessTokenAndRefreshToken(Integer userId, String accessToken, String refreshToken) {
        JwtClaims jwtClaims = null;
        if (refreshToken != null) {
            try {
                jwtClaims = refreshTokenProvider.getJwtClaimsFromToken(refreshToken);
            } catch (JwtErrorException e) {
                if (!e.getErrorCode().equals(JwtErrorCode.EXPIRED_TOKEN)) {
                    throw e;
                }
            }
        }

        if (jwtClaims != null) {
            deleteRefreshToken(userId, jwtClaims);
        }

        deleteAccessToken(userId, accessToken);
    }

    private void deleteRefreshToken(Integer userId, JwtClaims jwtClaims) {
        Integer refreshTokenUserId = JwtClaimsParserUtil.getClaimsValue(jwtClaims, RefreshTokenClaimKeys.USER_ID.getValue(), Integer::parseInt);

        if (!userId.equals(refreshTokenUserId)) {
            throw new JwtErrorException(JwtErrorCode.WITHOUT_OWNERSHIP_REFRESH_TOKEN);
        }

        refreshTokenService.deleteAll(refreshTokenUserId);
    }

    private void deleteAccessToken(Integer userId, String accessToken) {
        LocalDateTime expiresAt = accessTokenProvider.getExpiryDate(accessToken);
        forbiddenTokenService.createForbiddenToken(userId, accessToken, expiresAt);
    }

    private long toSeconds(LocalDateTime expiryTime) {
        return Duration.between(LocalDateTime.now(), expiryTime).getSeconds();
    }
}
