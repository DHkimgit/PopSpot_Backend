package io.devtab.popspot.domain.company.service;

import org.springframework.stereotype.Service;

import io.devtab.popspot.domain.company.dto.CompanyCommand;
import io.devtab.popspot.domain.company.service.implement.CompanyAppender;
import io.devtab.popspot.domain.company.service.implement.CompanyGetter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyAppender companyAppender;
    private final CompanyGetter companyGetter;

    public void registerCompany(CompanyCommand.register request) {
        companyAppender.append(request);
    }

    public Boolean checkBusinessNumber(CompanyCommand.checkBusinessNumber request) {
        return companyGetter.checkBusinessNumber(request.businessRegistrationNumber());
    }
}
