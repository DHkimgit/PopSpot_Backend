package io.devtab.popspot.domain.company.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.devtab.popspot.domain.company.dto.CompanyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Tag(name = "회사 API")
public interface CompanyApi {

    @Operation(summary = "회사 등록", description = "새로운 회사 정보 등록")
    @ApiResponse(responseCode = "201", description = "회사 등록 성공",
        content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "성공", value = """
                {
                    "code": "201",
                    "data": {
                        "CompanyAccountInviteCode": "CP-EIN7F6"
                      }
                }
                """, description = "회사 계정 권한을 부여 할 수 있는 코드 반환")
        }))
    @ApiResponse(responseCode = "400", description = "잘못된 요청",
        content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "유효성 검증 실패", value = """
                {
                    "code": "400",
                    "message": "잘못된 요청입니다",
                    "errors": [
                        {
                            "field": "businessRegistrationNumber",
                            "message": "사업자등록번호 형식이 올바르지 않습니다."
                        }
                    ]
                }
                """)
        }))
    ResponseEntity<?> register(@RequestBody @Validated CompanyRequest.Register request);

    @Operation(summary = "사업자 번호 중복 체크", description = "사업자 번호 중복 확인")
    @Validated
    @ApiResponse(responseCode = "200", description = "사업자 번호 중복 확인 성공",
        content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "중복됨", value = """
                {
                    "code": "200",
                      "data": {
                        "is_duplicated": true
                      }
                }
                """),
            @ExampleObject(name = "중복되지 않음", value = """
                {
                    "code": "200",
                      "data": {
                        "is_duplicated": false
                      }
                }
                """)
        }))
    @ApiResponse(responseCode = "400", description = "잘못된 요청",
        content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "사업자 번호 형식 오류", value = """
                {
                    "code": "400",
                      "message": "사업자 등록 번호 형식이 올바르지 않습니다.",
                      "fieldErrors": null
                }
                """),
            @ExampleObject(name = "빈 값으로 요청", value = """
                {
                    "code": "400",
                      "message": "사업자 등록 번호를 입력해주세요.",
                      "fieldErrors": null
                }
                """)
        }))
    ResponseEntity<?> checkBusinessNumber(
        @PathVariable
        @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}$", message = "사업자 등록 번호 형식이 올바르지 않습니다.")
        @NotBlank(message = "사업자 등록 번호를 입력해주세요.")
        String businessNumber
    );
}
