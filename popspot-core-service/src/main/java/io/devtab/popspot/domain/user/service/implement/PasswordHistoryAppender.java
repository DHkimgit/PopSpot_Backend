package io.devtab.popspot.domain.user.service.implement;

import org.springframework.stereotype.Component;

import io.devtab.popspot.domain.user.entity.UserPasswordHistory;
import io.devtab.popspot.domain.user.repository.PasswordHistoryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordHistoryAppender {

    private final PasswordHistoryRepository historyRepository;

    public void save(UserPasswordHistory passwordHistory) {
        historyRepository.save(passwordHistory);
    }
}
