package io.devtab.popspot.domain.user.exception;

import io.devtab.popspot.global.exception.ErrorCausedBy;
import io.devtab.popspot.global.exception.GlobalErrorException;

public class AuthErrorException extends GlobalErrorException {

    private final UserErrorCode userErrorCode;

    public AuthErrorException(UserErrorCode userErrorCode) {
        super(userErrorCode);
        this.userErrorCode = userErrorCode;
    }

    public ErrorCausedBy causedBy() {
        return userErrorCode.causedBy();
    }

    public String getExplainError() {
        return userErrorCode.getExplainError();
    }
}
