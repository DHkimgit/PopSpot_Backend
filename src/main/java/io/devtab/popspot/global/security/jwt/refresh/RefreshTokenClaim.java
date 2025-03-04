package io.devtab.popspot.global.security.jwt.refresh;

import static io.devtab.popspot.global.security.jwt.refresh.RefreshTokenClaimKeys.*;

import java.util.Map;

import io.devtab.popspot.global.security.jwt.model.JwtClaims;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenClaim implements JwtClaims {
    private final Map<String, ?> claims;

    public static RefreshTokenClaim of(Integer userId, String role) {
        Map<String, Object> claims = Map.of(
            USER_ID.getValue(), userId.toString(),
            ROLE.getValue(), role
        );
        return new RefreshTokenClaim(claims);
    }

    @Override
    public Map<String, ?> getClaims() {
        return claims;
    }
}
