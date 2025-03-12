package io.devtab.popspot.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class UserUpdateDto {

    @Schema(title = "비밀번호 변경 요청 DTO")
    public record PasswordRequest(
        @Schema(description = "현재 비밀번호. 공백 문자만 포함되어 있으면 안 됨", example = "password")
        @NotBlank(message = "현재 비밀번호를 입력해주세요")
        String oldPassword,
        @Schema(description = "새 비밀번호. 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해주세요. (적어도 하나의 영문 소문자, 숫자 포함)", example = "newPassword")
        @NotBlank(message = "새 비밀번호를 입력해주세요")
        String newPassword
    ) {
    }
}
