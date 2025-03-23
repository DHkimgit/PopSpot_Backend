package io.devtab.popspot.domain.user.service.implement;

import org.springframework.stereotype.Component;

import io.devtab.popspot.domain.user.entity.UserDeletion;
import io.devtab.popspot.domain.user.repository.UserDeletionRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDeletionAppender {

    private final UserDeletionRepository userDeletionRepository;

    public UserDeletion save(UserDeletion userDeletion) {
        return userDeletionRepository.save(userDeletion);
    }
}
