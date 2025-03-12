package io.devtab.popspot.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.devtab.popspot.domain.user.entity.UserPasswordHistory;

public interface PasswordHistoryRepository extends JpaRepository<UserPasswordHistory, Integer> {

    @Query(value = "SELECT * FROM user_password_history WHERE user_id := userId ORDER BY created_at DESC LIMIT 1", nativeQuery = true)
    Optional<UserPasswordHistory> findRecentPasswordHistoryByUserId(@Param("userId") Integer userId);
}
