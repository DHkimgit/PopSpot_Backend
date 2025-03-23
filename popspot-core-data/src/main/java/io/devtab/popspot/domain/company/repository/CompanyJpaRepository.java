package io.devtab.popspot.domain.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.devtab.popspot.domain.company.entity.Company;

public interface CompanyJpaRepository extends JpaRepository<Company, Integer> {

    public Boolean existsByBusinessRegistrationNumber(String businessRegistrationNumber);
}
