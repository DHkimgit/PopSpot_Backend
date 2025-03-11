package io.devtab.popspot.domain.user.service.implement;

import io.devtab.popspot.domain.user.model.UserPasswordHistory;
import io.devtab.popspot.domain.user.repository.PasswordHistoryRepository;
import io.devtab.popspot.global.annotation.ServiceImplement;
import lombok.RequiredArgsConstructor;

@ServiceImplement
@RequiredArgsConstructor
public class PasswordHistoryAppender {

    private final PasswordHistoryRepository historyRepository;

    public void save(UserPasswordHistory passwordHistory) {
        historyRepository.save(passwordHistory);
    }
}
