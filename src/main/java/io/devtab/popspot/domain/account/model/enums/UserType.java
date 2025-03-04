package io.devtab.popspot.domain.account.model.enums;

import lombok.Getter;

@Getter
public enum UserType {
    USER(Authority.USER), PARTNER(Authority.HOST), ADMIN(Authority.ADMIN);

    private final String authority;

    UserType(String authority) {
        this.authority = authority;
    }

    public static class Authority {
        public static final String USER = "USER";
        public static final String HOST = "HOST";
        public static final String ADMIN = "ADMIN";
    }
}
