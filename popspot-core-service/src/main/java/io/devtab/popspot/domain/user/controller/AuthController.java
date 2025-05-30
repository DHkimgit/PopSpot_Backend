package io.devtab.popspot.domain.user.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.devtab.popspot.domain.user.api.AuthApi;
import io.devtab.popspot.domain.user.dto.UserSignInRequest;
import io.devtab.popspot.domain.user.dto.UserSignUpRequest;
import io.devtab.popspot.domain.user.service.AuthService;
import io.devtab.popspot.global.response.SuccessResponse;
import io.devtab.popspot.global.security.jwt.dto.JwtTokens;
import io.devtab.popspot.global.util.CookieUtil;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthApi {

    private final CookieUtil cookieUtil;
    private final AuthService authService;

    @PostMapping("/sign-up")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<?> signUp(UserSignUpRequest.TypeUser request) {
        return createAuthenticatedResponse(authService.signUpUser(request.toInfo()));
    }

    @PostMapping("/sign-in")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<?> signIn(UserSignInRequest request) {
        return createAuthenticatedResponse(authService.signIn(request.email(), request.password()));
    }

    private ResponseEntity<?> createAuthenticatedResponse(JwtTokens tokens) {
        ResponseCookie cookie = cookieUtil.createCookie("refreshToken", tokens.refreshToken(), Duration.ofDays(7).toSeconds());
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .header(HttpHeaders.AUTHORIZATION, tokens.accessToken())
            .body(SuccessResponse.from("user", Map.of("id", tokens.userId())));
    }
}
