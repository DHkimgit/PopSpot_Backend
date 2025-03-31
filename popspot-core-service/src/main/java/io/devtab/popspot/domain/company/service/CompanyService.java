package io.devtab.popspot.domain.company.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.devtab.popspot.domain.company.dto.CompanyCommand;
import io.devtab.popspot.domain.company.entity.Company;
import io.devtab.popspot.domain.company.entity.EmployeeAuthority;
import io.devtab.popspot.domain.company.redis.CompanyAccountInviteCode;
import io.devtab.popspot.domain.company.service.implement.CompanyAccountInviteCodeManager;
import io.devtab.popspot.domain.company.service.implement.CompanyAppender;
import io.devtab.popspot.domain.company.service.implement.CompanyGetter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyAppender companyAppender;
    private final CompanyGetter companyGetter;
    private final CompanyAccountInviteCodeManager accountInviteCodeManager;

    @Transactional
    public String registerCompany(CompanyCommand.register request) {
        Company company = companyAppender.append(request);
        CompanyAccountInviteCode accountInviteCode = accountInviteCodeManager.generateAndSaveCode(
            company.getId(), EmployeeAuthority.HOST_ADMIN);
        return accountInviteCode.getCode();
    }

    public Boolean checkBusinessNumber(CompanyCommand.checkBusinessNumber request) {
        return companyGetter.checkBusinessNumber(request.businessRegistrationNumber());
    }
}
