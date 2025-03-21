package io.devtab.popspot.domain.user.entity;

import static lombok.AccessLevel.PROTECTED;

import java.time.LocalDateTime;

import io.devtab.popspot.domain.user.entity.enums.DeletionStatus;

import io.devtab.popspot.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_deletions")
@NoArgsConstructor(access = PROTECTED)
public class UserDeletion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",  nullable = false)
    private User user;

    @Column(name = "reason", nullable = false, length = 255)
    private String reason;

    @Column(name = "deleted_at", nullable = false)
    private LocalDateTime deletedAt;

    @Column(name = "feedback")
    private String feedback;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "device_info", length = 255)
    private String deviceInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 45)
    private DeletionStatus status;

    @Builder
    public UserDeletion(User user, String reason, LocalDateTime deletedAt, String feedback, String ipAddress,
        String deviceInfo, DeletionStatus status) {
        this.user = user;
        this.reason = reason;
        this.deletedAt = deletedAt;
        this.feedback = feedback;
        this.ipAddress = ipAddress;
        this.deviceInfo = deviceInfo;
        this.status = status;
    }
}
