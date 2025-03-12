package io.devtab.popspot.global.exception;

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
