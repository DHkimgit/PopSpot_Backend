package io.devtab.popspot.domain.user.service.implement;

import org.springframework.transaction.annotation.Transactional;

import io.devtab.popspot.domain.user.model.User;
import io.devtab.popspot.domain.user.repository.UserRepository;
import io.devtab.popspot.global.annotation.ServiceImplement;
import lombok.RequiredArgsConstructor;

@ServiceImplement
@RequiredArgsConstructor
public class UserAppender {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
