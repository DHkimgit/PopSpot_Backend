package io.devtab.popspot.domain.user.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.devtab.popspot.domain.user.api.AuthApi;
import io.devtab.popspot.domain.user.dto.SignInRequest;
import io.devtab.popspot.domain.user.dto.SignUpRequest;
import io.devtab.popspot.domain.user.service.AuthService;
import io.devtab.popspot.global.security.jwt.dto.JwtTokens;
import io.devtab.popspot.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthApi {

    private final CookieUtil cookieUtil;
    private final AuthService authService;

    @Override
    public ResponseEntity<?> signUp(SignUpRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> signIn(SignInRequest request) {
        return null;
    }

    private ResponseEntity<?> createAuthenticatedResponse(JwtTokens tokens) {
        ResponseCookie cookie = cookieUtil.createCookie("refreshToken", tokens.refreshToken(), Duration.ofDays(7).toSeconds());
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .header(HttpHeaders.AUTHORIZATION, tokens.accessToken())
            .body(SuccessResponse.from("user", Map.of("id", userInfo.getKey())));
    }
}
