package io.devtab.popspot.domain.company.service.implement;

import org.springframework.stereotype.Component;

import io.devtab.popspot.domain.company.entity.EmployeeAuthority;
import io.devtab.popspot.domain.company.redis.CompanyAccountInviteCode;
import io.devtab.popspot.domain.company.redis.CompanyAccountInviteCodeRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CompanyAccountInviteCodeManager {

    private final CompanyAccountInviteCodeRepository codeRepository;
    private final CompanyAccountInviteCodeGenerator codeGenerator;

    public CompanyAccountInviteCode generateAndSaveCode(Integer companyId, EmployeeAuthority authority) {
        String randomCode = codeGenerator.generateCode();
        CompanyAccountInviteCode code = CompanyAccountInviteCode.createCode(randomCode, companyId, authority);
        return codeRepository.save(code);
    }
}
