package io.devtab.popspot.domain.user.service.implement;

import java.util.Optional;

import org.springframework.stereotype.Component;

import io.devtab.popspot.domain.user.entity.UserPasswordHistory;
import io.devtab.popspot.domain.user.repository.PasswordHistoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordHistoryGetter {

    private final PasswordHistoryRepository passwordHistoryRepository;

    public Optional<UserPasswordHistory> getLatestPasswordHistory(Integer userId) {
        return passwordHistoryRepository.findRecentPasswordHistoryByUserId(userId);
    }
}
