package io.devtab.popspot.global.security.jwt.dto;

public record TokenRefreshResponse (
    JwtTokens tokens,
    Integer userId
) {
    public static TokenRefreshResponse of(JwtTokens tokens, Integer userId) {
        return new TokenRefreshResponse(tokens, userId);
    }
}
