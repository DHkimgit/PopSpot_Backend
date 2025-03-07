package io.devtab.popspot.domain.user.service.implement;

import java.util.Optional;

import io.devtab.popspot.domain.user.model.User;
import io.devtab.popspot.domain.user.repository.UserRepository;
import io.devtab.popspot.global.annotation.ServiceImplement;
import lombok.RequiredArgsConstructor;

@ServiceImplement
@RequiredArgsConstructor
public class UserGetter {

    private final UserRepository userRepository;

    public Optional<User> readByEmail(String email) {
        return userRepository.findActiveUserByEmail(email);
    }

    public User read(Integer userId) {
        return userRepository.getById(userId);
    }
}
