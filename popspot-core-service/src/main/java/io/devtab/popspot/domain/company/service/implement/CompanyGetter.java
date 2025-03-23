package io.devtab.popspot.domain.company.service.implement;

import org.springframework.stereotype.Component;

import io.devtab.popspot.domain.company.repository.CompanyJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CompanyGetter {

    private final CompanyJpaRepository companyRepository;

    public Boolean checkBusinessNumber(String businessNumber) {
        return companyRepository.existsByBusinessRegistrationNumber(businessNumber);
    }
}
