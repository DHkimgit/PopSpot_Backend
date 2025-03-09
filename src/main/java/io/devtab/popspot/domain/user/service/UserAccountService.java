package io.devtab.popspot.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import io.devtab.popspot.domain.user.model.User;
import io.devtab.popspot.domain.user.service.implement.PasswordHistoryGetter;
import io.devtab.popspot.domain.user.service.implement.UserGetter;
import io.devtab.popspot.global.annotation.Business;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserAccountService {

    private final PasswordEncoder passwordEncoder;
    private final UserGetter userGetter;
    private final PasswordHistoryGetter passwordHistoryGetter;

    @Transactional
    public void updatePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userGetter.read(userId);
        user.updatePassword(oldPassword, newPassword, passwordEncoder);
    }

    @Transactional(readOnly = true)
    public void validatePassword(Integer userId, String newPassword) {
        User user = userGetter.read(userId);

        user.validateNewPasswordDiffersFromCurrent(newPassword, passwordEncoder);

        passwordHistoryGetter.getLatestPasswordHistory(userId)
            .ifPresent(history -> history.validateNewPasswordDiffersFromHistory(newPassword, passwordEncoder));
    }
}
