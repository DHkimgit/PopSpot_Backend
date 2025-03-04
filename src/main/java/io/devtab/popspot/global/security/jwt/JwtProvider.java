package io.devtab.popspot.global.security.jwt;

import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

import io.devtab.popspot.global.security.jwt.model.JwtClaims;
import io.jsonwebtoken.Claims;

public interface JwtProvider {

    default String resolveToken(String authHeader) {
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring("Bearer ".length());
        }
        return "";
    }

    String generateToken(JwtClaims subs);

    JwtClaims getJwtClaimsFromToken(String token);

    LocalDateTime getExpiryDate(String token);

    boolean isTokenExpired(String token);

    Claims getClaimsFromToken(String token);
}
