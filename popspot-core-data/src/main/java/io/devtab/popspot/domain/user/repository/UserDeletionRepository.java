package io.devtab.popspot.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.devtab.popspot.domain.user.entity.UserDeletion;

public interface UserDeletionRepository extends JpaRepository<UserDeletion, Integer> {
}
