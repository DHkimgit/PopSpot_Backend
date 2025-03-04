package io.devtab.popspot.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.devtab.popspot.domain.account.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
