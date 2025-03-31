package io.devtab.popspot.domain.company.dto;

public class CompanyCommand {

    public record register(
        String name,
        String businessRegistrationNumber,
        String contactPhone,
        String contactEmail
    ) {

    }

    public record checkBusinessNumber(
        String businessRegistrationNumber
    ) {

    }
}
