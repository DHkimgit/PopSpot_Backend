package io.devtab.popspot.domain.company.service.implement;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.devtab.popspot.domain.company.dto.CompanyCommand;
import io.devtab.popspot.domain.company.entity.Company;
import io.devtab.popspot.domain.company.repository.CompanyJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CompanyAppender {

    private final CompanyJpaRepository companyRepository;

    @Transactional
    public Company append(CompanyCommand.register request) {
        Company entity = Company.builder()
            .name(request.name())
            .contactEmail(request.contactEmail())
            .contactPhone(request.contactPhone())
            .businessRegistrationNumber(request.businessRegistrationNumber())
            .isDeleted(false)
            .build();

        return companyRepository.save(entity);
    }
}
