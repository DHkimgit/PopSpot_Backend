package io.devtab.popspot.domain.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.devtab.popspot.domain.user.dto.SignUpRequest;
import io.devtab.popspot.domain.user.entity.User;
import io.devtab.popspot.global.exception.AuthErrorException;
import io.devtab.popspot.global.exception.UserErrorCode;
import io.devtab.popspot.domain.user.service.implement.UserAppender;
import io.devtab.popspot.domain.user.service.implement.UserGetter;
import io.devtab.popspot.global.security.jwt.JwtAuthManager;
import io.devtab.popspot.global.security.jwt.dto.JwtTokens;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtAuthManager jwtAuthManager;
    private final UserGetter userGetter;
    private final UserAppender userAppender;

    public JwtTokens signIn(String email, String password) {
        Optional<User> user = userGetter.readByEmail(email);

        if(!isExistUser(user)) {
            throw new AuthErrorException(UserErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }

        if (!isValidPassword(user.get(), password)) {
            throw new AuthErrorException(UserErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }

        return jwtAuthManager.createToken(user.get());
    }

    public JwtTokens signUpUser(SignUpRequest.Info request) {
        User user = userAppender.createUser(request.toEntity(passwordEncoder));
        return jwtAuthManager.createToken(user);
    }

    private boolean isExistUser(Optional<User> user) {
        return user.isPresent();
    }

    private boolean isValidPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
