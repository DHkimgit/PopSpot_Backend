package io.devtab.popspot.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "SignInRequest", title = "로그인 요청")
public record SignInRequest(
    @Schema(description = "아이디(이메일)", example = "qwer1234")
    @NotBlank(message = "아이디(이메일)을 입력해주세요")
    String email,
    @Schema(description = "비밀번호", example = "1q2w3e4r!")
    @NotBlank(message = "비밀번호를 입력해주세요")
    String password
) {
}
