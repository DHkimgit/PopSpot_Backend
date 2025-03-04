package io.devtab.popspot.global.security.jwt.exception;

import io.devtab.popspot.global.exception.ErrorCausedBy;
import io.devtab.popspot.global.exception.GlobalErrorException;
import io.devtab.popspot.global.security.jwt.error.JwtErrorCode;

public class JwtErrorException extends GlobalErrorException {
    private final JwtErrorCode errorCode;

    public JwtErrorException(JwtErrorCode jwtErrorCode) {
        super(jwtErrorCode);
        this.errorCode = jwtErrorCode;
    }

    public ErrorCausedBy causedBy() {
        return errorCode.causedBy();
    }

    public JwtErrorCode getErrorCode() {
        return errorCode;
    }
}
