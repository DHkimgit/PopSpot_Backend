package io.devtab.popspot.domain.user.exception;

import io.devtab.popspot.global.exception.BaseErrorCode;
import io.devtab.popspot.global.exception.ErrorCausedBy;
import io.devtab.popspot.global.exception.GlobalErrorException;

public class UserErrorException extends GlobalErrorException {

    private final UserErrorCode userErrorCode;

    public UserErrorException(UserErrorCode userErrorCode) {
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
