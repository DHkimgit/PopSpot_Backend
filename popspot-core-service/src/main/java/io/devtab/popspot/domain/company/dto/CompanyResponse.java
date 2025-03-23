package io.devtab.popspot.domain.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CompanyResponse {

    @Schema(name = "companyRegisterResponse", title = "기업 회원가입 응답 DTO")
    public record register(
        @Schema(description = "회원 계정 권한 수정 코드", example = "CP-4RN93X")
        String CompanyAccountInviteCode
    ) {
        public static CompanyResponse.register of(String companyAccountInviteCode) {
            return new CompanyResponse.register(companyAccountInviteCode);
        }
    }
}
