package io.devtab.popspot.global.security.jwt.dto;

public record JwtTokens(
    String accessToken,
    String refreshToken,
    Integer userId
) {

    public static JwtTokens of(String accessToken, String refreshToken, Integer userId) {
        return new JwtTokens(accessToken, refreshToken, userId);
    }
}
