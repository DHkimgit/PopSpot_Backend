package io.devtab.popspot.domain.user.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.devtab.popspot.domain.user.dto.UserDeleteRequest;
import io.devtab.popspot.domain.user.dto.UserUpdateDto;
import io.devtab.popspot.global.security.SecurityUserDetails;
import io.devtab.popspot.global.web.ipaddress.IpAddress;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "사용자 계정 관리 API")
public interface UserAccountApi {

    @Operation(summary = "사용자 비밀번호 변경")
    @ApiResponses({
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "기존 비밀번호 불일치", value = """
                            {
                                "code": "400",
                                "message": "비밀번호가 일치하지 않습니다."
                            }
                            """),
            @ExampleObject(name = "변경 비밀번호가 기존 비밀번호와 동일한 경우", value = """
                            {
                                "code": "400",
                                "message": "현재 비밀번호와 동일한 비밀번호로 변경할 수 없습니다."
                            }
                            """)
        })),
    })
    ResponseEntity<?> changePassword(@RequestBody @Validated UserUpdateDto.PasswordRequest request, @AuthenticationPrincipal SecurityUserDetails user);

    @Operation(summary = "사용자 닉네임 중복 확인")
    @ApiResponses({
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "기존 닉네임과 중복", value = """
                            {
                                "code": "400",
                                "message": "중복된 닉네임 입니다."
                            }
                            """)
        })),
    })
    ResponseEntity<?> validateNickname(@RequestParam("nickname") String nickname);

    @Operation(summary = "사용자 탈퇴")
    @ApiResponses({
        @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "DTO 항목 누락", value = """
                            {
                                "code": "400",
                                "message": "탈퇴 사유를 입력해주세요."
                            }
                            """)
        })),
    })
    ResponseEntity<?> deleteUser(@RequestBody @Validated UserDeleteRequest request, @AuthenticationPrincipal SecurityUserDetails userDetails, @IpAddress String ipAddress);

}
