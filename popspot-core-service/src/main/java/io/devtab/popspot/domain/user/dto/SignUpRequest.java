package io.devtab.popspot.domain.user.dto;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import io.devtab.popspot.domain.user.entity.User;
import io.devtab.popspot.domain.user.entity.enums.UserGender;
import io.devtab.popspot.domain.user.entity.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SignUpRequest {

    public record Info(String email, String password, String name, String nickname, String phoneNumber, Boolean marketingAgree, UserGender gender) {
        public String password(PasswordEncoder passwordEncoder) {
            return passwordEncoder.encode(password);
        }

        public User toEntity(PasswordEncoder passwordEncoder) {
            return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .isDeleted(false)
                .marketingAgree(marketingAgree)
                .userType(UserType.USER)
                .lastLoggedAt(LocalDateTime.now())
                .passwordUpdateAt(LocalDateTime.now())
                .build();
        }
    }

    @Schema(name = "signUpRequestUser", title = "일반 사용자 회원가입 요청 DTO")
    public record TypeUser(
        @Schema(description = "이메일", example = "qwer1234@naver.com")
        @NotBlank(message = "이메일을 입력해주세요")
        @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "유효한 이메일 주소를 입력해 주세요")
        String email,
        @Schema(description = "이름", example = "홍길동")
        @NotBlank(message = "이름을 입력해주세요")
        @Pattern(regexp = "^[가-힣a-zA-Z]{2,8}$", message = "한글과 영문 대, 소문자만 가능해요")
        String name,
        @Schema(description = "닉네임", example = "홍길동")
        @NotBlank(message = "닉네임을 입력해주세요")
        @Pattern(regexp = "^[가-힣a-zA-Z]{2,8}$", message = "한글과 영문 대, 소문자만 가능해요")
        String nickname,
        @Schema(description = "비밀번호", example = "1q2w3e4r!")
        @NotBlank(message = "비밀번호를 입력해주세요")
        String password,
        @Schema(description = "전화번호", example = "010-1234-5678")
        @NotBlank(message = "전화번호를 입력해주세요")
        @Pattern(regexp = "^01[01]-\\d{4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
        String phone,
        @Schema(description = "성별", example = "MAN")
        UserGender gender,
        @Schema(description = "마케팅 수신 동의")
        Boolean marketingAgree
    ) {
        public Info toInfo() {
            return new Info(email, password, name, nickname, phone, marketingAgree, gender);
        }
    }
}
