package io.devtab.popspot.domain.user.service.implement;

import java.util.Optional;

import org.springframework.stereotype.Component;

import io.devtab.popspot.domain.user.entity.User;
import io.devtab.popspot.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public Optional<User> readByEmail(String email) {
        return userRepository.findActiveUserByEmail(email);
    }

    public User read(Integer userId) {
        return userRepository.getById(userId);
    }

    public Boolean existNickname(String nickname) {
        return userRepository.existsUserByNickname(nickname);
    }
}
