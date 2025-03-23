package io.devtab.popspot.domain.company.entity;

import lombok.Getter;

@Getter
public enum EmployeeAuthority {
    ADMIN("어드민"), MANAGER("매니저"), STAFF("직원");

    private final String name;

    EmployeeAuthority(String name) {
        this.name = name;
    }
}
