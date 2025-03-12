package io.devtab.popspot.global.exception;

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
