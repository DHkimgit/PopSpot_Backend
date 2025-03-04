package io.devtab.popspot.domain.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.devtab.popspot.domain.account.api.AuthApi;
import io.devtab.popspot.domain.account.dto.SignInRequest;
import io.devtab.popspot.domain.account.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthApi {

    @Override
    public ResponseEntity<?> signUp(SignUpRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<?> signIn(SignInRequest request) {
        return null;
    }
}
