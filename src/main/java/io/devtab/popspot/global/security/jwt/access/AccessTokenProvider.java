package io.devtab.popspot.global.security.jwt.access;

import static io.devtab.popspot.global.security.jwt.access.AccessTokenClaimKeys.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import io.devtab.popspot.global.security.jwt.JwtProvider;
import io.devtab.popspot.global.security.jwt.error.JwtErrorCode;
import io.devtab.popspot.global.security.jwt.error.JwtErrorCodeUtil;
import io.devtab.popspot.global.security.jwt.exception.JwtErrorException;
import io.devtab.popspot.global.security.jwt.model.JwtClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@AccessTokenStrategy
@Slf4j
@Primary
@Component
public class AccessTokenProvider implements JwtProvider {

    private final SecretKey secretKey;
    private final Duration tokenExpiration;

    public AccessTokenProvider(
        @Value("${jwt.secret-key}") String jwtSecretKey,
        @Value("${jwt.access-token.expiration-time}") Duration tokenExpiration
    ) {
        final byte[] secretKeyBytes = Base64.getDecoder().decode(jwtSecretKey);
        this.secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
        this.tokenExpiration = tokenExpiration;
    }

    @Override
    public String generateToken(JwtClaims claims) {
        Date now = new Date();

        return Jwts.builder()
            .header().add(createHeader()).and()
            .claims(claims.getClaims())
            .signWith(secretKey)
            .expiration(createExpireDate(now, tokenExpiration.toMillis()))
            .compact();
    }

    @Override
    public JwtClaims getJwtClaimsFromToken(String token) {
        Claims claims = getClaimsFromToken(token);

        return AccessTokenClaim.of(Integer.parseInt(claims.get(USER_ID.getValue(), String.class)), claims.get(ROLE.getValue(), String.class));
    }

    @Override
    public LocalDateTime getExpiryDate(String token) {
        Claims claims = getClaimsFromToken(token);
        return Instant.ofEpochMilli(claims.getExpiration().getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }

    @Override
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getExpiration().before(new Date());
        } catch (JwtErrorException e) {
            if (JwtErrorCode.EXPIRED_TOKEN.equals(e.getErrorCode())) return true;
            throw e;
        }
    }

    @Override
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (JwtException e) {
            final JwtErrorCode errorCode = JwtErrorCodeUtil.determineErrorCode(e, JwtErrorCode.FAILED_AUTHENTICATION);

            log.warn("Error code : {}, Error - {},  {}", errorCode, e.getClass(), e.getMessage());
            throw new JwtErrorException(errorCode);
        }
    }

    private Map<String, Object> createHeader() {
        return Map.of("typ", "JWT",
            "alg", "HS256",
            "regDate", System.currentTimeMillis());
    }

    private Date createExpireDate(final Date now, long expirationTime) {
        return new Date(now.getTime() + expirationTime);
    }
}
