package io.devtab.popspot.domain.user.exception;

import io.devtab.popspot.global.exception.BaseErrorCode;
import io.devtab.popspot.global.exception.ErrorCausedBy;
import io.devtab.popspot.global.exception.HttpStatusCode;
import io.devtab.popspot.global.exception.ReasonCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    /* 401 UNAUTHORIZED */
    INVALID_USERNAME_OR_PASSWORD(HttpStatusCode.UNAUTHORIZED, ReasonCode.MISSING_OR_INVALID_AUTHENTICATION_CREDENTIALS, "아이디 혹은 비밀번호가 적합하지 않습니다.");

    private final HttpStatusCode statusCode;
    private final ReasonCode reasonCode;
    private final String message;

    @Override
    public ErrorCausedBy causedBy() {
        return ErrorCausedBy.of(statusCode, reasonCode);
    }

    @Override
    public String getExplainError() throws NoSuchFieldError {
        return message;
    }
}
