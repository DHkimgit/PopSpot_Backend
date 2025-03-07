package io.devtab.popspot.domain.user.service.implement;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordPolicy {

    private final PasswordEncoder passwordEncoder;

    public boolean isSamePassword(String actual, String changed) {
        return passwordEncoder.matches(actual, changed);
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
