package io.devtab.popspot.domain.user.entity;

import static io.devtab.popspot.global.exception.UserErrorCode.IS_FORMER_PASSWORD;
import static lombok.AccessLevel.PROTECTED;

import org.springframework.security.crypto.password.PasswordEncoder;


import io.devtab.popspot.global.BaseEntity;
import io.devtab.popspot.global.exception.UserErrorException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_password_history")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = PROTECTED)
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

    @Builder
    public UserPasswordHistory(Integer id, User user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

    public static UserPasswordHistory of(User user, String password) {
        return UserPasswordHistory.builder()
            .user(user)
            .password(password)
            .build();
    }
}
