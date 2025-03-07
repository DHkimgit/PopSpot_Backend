package io.devtab.popspot.domain.user.service;

import static io.devtab.popspot.domain.user.exception.UserErrorCode.IS_SAME_PASSWORD;
import static io.devtab.popspot.domain.user.exception.UserErrorCode.NOT_MATCHED_PASSWORD;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import io.devtab.popspot.domain.user.exception.UserErrorException;
import io.devtab.popspot.domain.user.model.User;
import io.devtab.popspot.domain.user.service.implement.PasswordPolicy;
import io.devtab.popspot.domain.user.service.implement.UserGetter;
import io.devtab.popspot.global.annotation.Business;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserAccountService {

    private final PasswordPolicy passwordPolicy;
    private final PasswordEncoder passwordEncoder;
    private final UserGetter userGetter;

    @Transactional
    public void updatePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userGetter.read(userId);

        checkCurrentPassword(oldPassword, user.getPassword());
        checkSamePassword(oldPassword, newPassword);
        user.updatePassword(passwordEncoder.encode(newPassword));
    }

    private void checkCurrentPassword(String password, String storedPassword) {
        if(!passwordEncoder.matches(password, storedPassword)) {
            throw new UserErrorException(NOT_MATCHED_PASSWORD);
        }
    }

    private void checkSamePassword(String oldPassword, String newPassword) {
        if(passwordPolicy.isSamePassword(oldPassword, newPassword)) {
           throw new UserErrorException(IS_SAME_PASSWORD);
        }
    }
}
