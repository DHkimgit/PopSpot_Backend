package io.devtab.popspot.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.devtab.popspot.domain.user.exception.UserErrorCode;
import io.devtab.popspot.domain.user.exception.UserErrorException;
import io.devtab.popspot.domain.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users u WHERE u.is_deleted = 0 AND u.email = :email", nativeQuery = true)
    Optional<User> findActiveUserByEmail(@Param("email") String email);

    default User getById(Integer userId) {
        return findById(userId).orElseThrow(() -> new UserErrorException(UserErrorCode.USER_NOT_FOUND));
    }
}
