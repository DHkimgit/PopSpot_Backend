package io.devtab.popspot.domain.company.entity;

import lombok.Getter;

@Getter
public enum EmployeeAuthority {
    HOST_ADMIN("어드민"), HOST_MANAGER("매니저"), HOST_STAFF("직원");

    private final String name;

    EmployeeAuthority(String name) {
        this.name = name;
    }
}
