package io.devtab.popspot.domain.user.model;

import static io.devtab.popspot.domain.user.exception.UserErrorCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import io.devtab.popspot.domain.user.exception.UserErrorException;
import io.devtab.popspot.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user_password_history")
public class UserPasswordHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    public void validateNewPasswordDiffersFromHistory(String newPassword, PasswordEncoder passwordEncoder) {
        if(passwordEncoder.matches(this.password, newPassword)) {
            throw new UserErrorException(IS_FORMER_PASSWORD);
        }
    }
}
