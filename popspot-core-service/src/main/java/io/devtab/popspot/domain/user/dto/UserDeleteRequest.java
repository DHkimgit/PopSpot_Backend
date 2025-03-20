package io.devtab.popspot.domain.user.dto;

import java.time.LocalDateTime;

import io.devtab.popspot.domain.user.entity.User;
import io.devtab.popspot.domain.user.entity.UserDeletion;
import io.devtab.popspot.domain.user.entity.enums.DeletionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "UserDeleteRequest", title = "일반 사용자 탈퇴 요청 DTO")
public record UserDeleteRequest (
    @Schema(description = "탈퇴 사유", example = "서비스가 필요하지 않습니다.")
    @NotBlank(message = "탈퇴 사유를 입력해주세요")
    String reason,
    @Schema(description = "피드백", example = "디자인이 이상해요.")
    String feedback,
    @Schema(description = "디바이스 정보", example = "PC")
    @NotBlank(message = "디바이스 정보를 입력해주세요")
    String deviceInfo
){
    public UserDeletion toEntity(User user, String ipAddress) {
        return UserDeletion.builder()
            .user(user)
            .reason(reason)
            .deletedAt(LocalDateTime.now())
            .feedback(feedback)
            .ipAddress(ipAddress)
            .deviceInfo(deviceInfo)
            .status(DeletionStatus.PENDING)
            .build();
    }
}
