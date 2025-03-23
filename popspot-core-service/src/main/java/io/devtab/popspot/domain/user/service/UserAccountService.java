package io.devtab.popspot.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.devtab.popspot.domain.user.entity.User;
import io.devtab.popspot.domain.user.entity.UserPasswordHistory;
import io.devtab.popspot.domain.user.service.implement.PasswordHistoryAppender;
import io.devtab.popspot.domain.user.service.implement.PasswordHistoryGetter;
import io.devtab.popspot.domain.user.service.implement.UserReader;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final PasswordEncoder passwordEncoder;
    private final UserReader userReader;
    private final PasswordHistoryGetter passwordHistoryGetter;
    private final PasswordHistoryAppender passwordHistoryAppender;

    @Transactional
    public void updatePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userReader.read(userId);
        UserPasswordHistory latestHistory = passwordHistoryGetter.getLatestPasswordHistory(userId).orElse(null);
        UserPasswordHistory updatedHistory = user.updatePassword(oldPassword, newPassword, passwordEncoder,
            latestHistory);
        passwordHistoryAppender.save(updatedHistory);
    }

    @Transactional(readOnly = true)
    public Boolean validateNickname(String nickname) {
        return userReader.existNickname(nickname);
    }
}
