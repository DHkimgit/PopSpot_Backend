package io.devtab.popspot.domain.user.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import io.devtab.popspot.domain.user.dto.SignInRequest;
import io.devtab.popspot.domain.user.dto.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface AuthApi {

    @Operation(summary = "회원가입", description = "일반 사용자 회원가입")
    @ApiResponse(responseCode = "200", description = "회원가입 성공",
        headers = {
            @Header(name = "Set-Cookie", description = "리프레시 토큰", schema = @Schema(type = "string"), required = true),
            @Header(name = "Authorization", description = "액세스 토큰", schema = @Schema(type = "string", format = "jwt"), required = true)
        },
        content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "성공", value = """
                            {
                                "code": "200",
                                "data": {
                                    "user": {
                                        "id": 1
                                    }
                                }
                            }
                            """)
        }))
    ResponseEntity<?> signUp(@RequestBody @Validated SignUpRequest.TypeUser request);

    @Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "로그인 성공",
            headers = {
                @Header(name = "Set-Cookie", description = "리프레시 토큰", schema = @Schema(type = "string"), required = true),
                @Header(name = "Authorization", description = "액세스 토큰", schema = @Schema(type = "string", format = "jwt"), required = true)
            },
            content = @Content(mediaType = "application/json", examples = {
                @ExampleObject(name = "성공", value = """
                                    {
                                        "code": "200",
                                        "data": {
                                            "user": {
                                                "id": 1
                                            }
                                        }
                                    }
                                    """)
            })),
        @ApiResponse(responseCode = "401", description = "로그인 실패", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "실패 - 유효하지 않은 아이디/비밀번호", value = """
                            {
                                "code": "401",
                                "message": "유효하지 않은 아이디 또는 비밀번호입니다."
                            }
                            """)
        }))
    })
    ResponseEntity<?> signIn(@RequestBody @Validated SignInRequest request);
}
