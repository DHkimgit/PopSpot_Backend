package io.devtab.popspot.domain.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CompanyRequest {

    @Schema(name = "companyRegisterRequest", title = "기업 회원가입 요청 DTO")
    public record Register(
        @Schema(description = "기업명", example = "OO 주식회사")
        @NotBlank(message = "기업명을 입력해주세요")
        String name,

        @Schema(description = "사업자 등록 번호", example = "123-45-67890")
        @NotBlank(message = "사업자 등록 번호를 입력해주세요")
        @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}$", message = "사업자 등록 번호 형식이 올바르지 않습니다.")
        String businessRegistrationNumber,

        @Schema(description = "연락처", example = "02-1234-5678")
        @NotBlank(message = "연락처를 입력해주세요")
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "연락처 형식이 올바르지 않습니다.")
        String contactPhone,

        @Schema(description = "이메일", example = "company@example.com")
        @NotBlank(message = "이메일을 입력해주세요")
        @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "유효한 이메일 주소를 입력해 주세요")
        String contactEmail
    ) {
        public CompanyCommand.register toCommand() {
            return new CompanyCommand.register(name, businessRegistrationNumber, contactPhone, contactEmail);
        }
    }

    @Schema(name = "companyBusinessNumberCheckRequest", title = "사업자등록번호 중복 확인 요청 DTO")
    public record BusinessNumberCheck(
        @Schema(description = "사업자 등록 번호", example = "123-45-67890")
        @NotBlank(message = "사업자 등록 번호를 입력해주세요")
        @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}$", message = "사업자 등록 번호 형식이 올바르지 않습니다.")
        String businessRegistrationNumber
    ) {

        public static CompanyCommand.checkBusinessNumber validateAndReturnCommand(String businessRegistrationNumber) {
            var request = new CompanyRequest.BusinessNumberCheck(businessRegistrationNumber);
            return request.toCommand();
        }

        public CompanyCommand.checkBusinessNumber toCommand() {
            return new CompanyCommand.checkBusinessNumber(businessRegistrationNumber);
        }
    }
}
