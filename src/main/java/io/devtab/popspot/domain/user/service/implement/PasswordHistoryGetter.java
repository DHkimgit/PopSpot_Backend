package io.devtab.popspot.domain.user.service.implement;

import java.util.Optional;

import io.devtab.popspot.domain.user.model.UserPasswordHistory;
import io.devtab.popspot.domain.user.repository.PasswordHistoryRepository;
import io.devtab.popspot.global.annotation.ServiceImplement;
import lombok.RequiredArgsConstructor;

@ServiceImplement
@RequiredArgsConstructor
public class PasswordHistoryGetter {

    private final PasswordHistoryRepository passwordHistoryRepository;

    public Optional<UserPasswordHistory> getLatestPasswordHistory(Integer userId) {
        return passwordHistoryRepository.findRecentPasswordHistoryByUserId(userId);
    }
}
