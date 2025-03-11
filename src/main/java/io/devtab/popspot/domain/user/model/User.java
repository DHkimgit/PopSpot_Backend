package io.devtab.popspot.domain.user.model;

import static io.devtab.popspot.domain.user.exception.UserErrorCode.*;
import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import io.devtab.popspot.domain.user.exception.UserErrorException;
import io.devtab.popspot.domain.user.model.enums.UserGender;
import io.devtab.popspot.domain.user.model.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "nickname", nullable = false, length = 50, unique = true)
    private String nickname;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, length = 255)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 6)
    private UserGender gender;

    @Column(name = "marketing_agree", columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean marketingAgree;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isDeleted;

    @Column(name = "last_logged_at")
    private LocalDateTime lastLoggedAt;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "password_update_at", nullable = false)
    private LocalDateTime passwordUpdateAt;

    @Builder
    public User(String password, String name, String nickname, String phoneNumber, String email, UserType userType,
        UserGender gender, Boolean marketingAgree, Boolean isDeleted, LocalDateTime lastLoggedAt,
        LocalDateTime passwordUpdateAt) {
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userType = userType;
        this.gender = gender;
        this.marketingAgree = marketingAgree;
        this.isDeleted = isDeleted;
        this.lastLoggedAt = lastLoggedAt;
        this.passwordUpdateAt = passwordUpdateAt;
    }

    public UserPasswordHistory updatePassword(String currentPassword, String newPassword,
        PasswordEncoder passwordEncoder, UserPasswordHistory latestHistory) {
        validatePasswordNotEmpty(newPassword);
        validateCurrentPassword(currentPassword, passwordEncoder);
        if (latestHistory != null) {
            validateNewPasswordDiffersFromHistory(newPassword, latestHistory, passwordEncoder);
        }
        validateNewPasswordDiffersFromCurrent(newPassword, passwordEncoder);

        String oldPassword = this.password;
        this.password = passwordEncoder.encode(newPassword);
        this.passwordUpdateAt = LocalDateTime.now();

        return UserPasswordHistory.of(this, oldPassword);
    }

    private void validatePasswordNotEmpty(String newPassword) {
        if (!StringUtils.hasText(newPassword)) {
            throw new IllegalArgumentException("password는 null이거나 빈 문자열이 될 수 없습니다.");
        }
    }

    private void validateCurrentPassword(String currentPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(this.password, currentPassword)) {
            throw new UserErrorException(NOT_MATCHED_PASSWORD);
        }
    }

    public void validateNewPasswordDiffersFromCurrent(String newPassword, PasswordEncoder passwordEncoder) {
        if (passwordEncoder.matches(this.password, newPassword)) {
            throw new UserErrorException(IS_SAME_PASSWORD);
        }
    }

    private void validateNewPasswordDiffersFromHistory(String newPassword, UserPasswordHistory latestHistory,
        PasswordEncoder passwordEncoder) {
        if (passwordEncoder.matches(newPassword, latestHistory.getPassword())) {
            throw new UserErrorException(IS_FORMER_PASSWORD);
        }
    }

    @PrePersist
    protected void onCreate() {
        this.registeredAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.isDeleted == null) {
            this.isDeleted = false;
        }
        if (this.marketingAgree == null) {
            this.marketingAgree = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
