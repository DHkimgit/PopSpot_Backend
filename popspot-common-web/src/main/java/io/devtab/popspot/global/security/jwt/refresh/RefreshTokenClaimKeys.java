package io.devtab.popspot.global.security.jwt.refresh;

import lombok.Getter;

@Getter
public enum RefreshTokenClaimKeys {
    USER_ID("id"),
    ROLE("role");

    private final String value;

    RefreshTokenClaimKeys(String value) {
        this.value = value;
    }
}
