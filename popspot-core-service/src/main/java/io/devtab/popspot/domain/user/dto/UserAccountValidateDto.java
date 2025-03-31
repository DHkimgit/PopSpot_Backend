package io.devtab.popspot.domain.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class UserAccountValidateDto {

    @Schema(title = "닉네임 중복 검사 응답 DTO")
    @JsonNaming(value = SnakeCaseStrategy.class)
    public record NicknameResponse(
        @Schema(description = "닉네임 중복 여부", example = "홍길동")
        @NotBlank
        Boolean isDuplicate
    ) {
    }
}
