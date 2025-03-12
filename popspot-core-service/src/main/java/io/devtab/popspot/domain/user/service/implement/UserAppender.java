package io.devtab.popspot.domain.user.service.implement;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.devtab.popspot.domain.user.entity.User;
import io.devtab.popspot.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAppender {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
