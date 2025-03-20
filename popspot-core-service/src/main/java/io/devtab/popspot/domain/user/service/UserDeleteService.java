package io.devtab.popspot.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.devtab.popspot.domain.user.dto.UserDeleteRequest;
import io.devtab.popspot.domain.user.entity.User;
import io.devtab.popspot.domain.user.service.implement.PasswordHistoryRemover;
import io.devtab.popspot.domain.user.service.implement.UserDeletionAppender;
import io.devtab.popspot.domain.user.service.implement.UserReader;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDeleteService {

    private final UserReader userReader;
    private final UserDeletionAppender userDeletionAppender;
    private final PasswordHistoryRemover passwordHistoryRemover;

    @Transactional
    public void deleteUser(UserDeleteRequest request, Integer userId, String ipAddress) {
        User user = userReader.read(userId);
        userDeletionAppender.save(request.toEntity(user, ipAddress));
        passwordHistoryRemover.removeAll(user);
        user.cancelMembership();
    }
}
