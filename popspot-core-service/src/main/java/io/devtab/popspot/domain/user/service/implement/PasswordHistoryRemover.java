package io.devtab.popspot.domain.user.service.implement;

import org.springframework.stereotype.Component;

import io.devtab.popspot.domain.user.entity.User;
import io.devtab.popspot.domain.user.repository.PasswordHistoryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordHistoryRemover {

    private final PasswordHistoryRepository passwordHistoryRepository;

    public void removeAll(User user) {
        passwordHistoryRepository.deleteAllByUser(user);
    }
}
