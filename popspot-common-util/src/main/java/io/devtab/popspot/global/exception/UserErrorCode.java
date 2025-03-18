package io.devtab.popspot.global.exception;

import static io.devtab.popspot.global.exception.HttpStatusCode.*;

import io.devtab.popspot.global.exception.BaseErrorCode;
import io.devtab.popspot.global.exception.ErrorCausedBy;
import io.devtab.popspot.global.exception.HttpStatusCode;
import io.devtab.popspot.global.exception.ReasonCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    /* 400 BAD_REQUEST */
    NOT_MATCHED_PASSWORD(HttpStatusCode.BAD_REQUEST, ReasonCode.INVALID_REQUEST, "비밀번호가 일치하지 않습니다."),
    IS_SAME_PASSWORD(HttpStatusCode.BAD_REQUEST, ReasonCode.INVALID_REQUEST, "동일한 비밀번호로 변경할 수 없습니다."),
    IS_FORMER_PASSWORD(HttpStatusCode.BAD_REQUEST, ReasonCode.INVALID_REQUEST, "과거에 사용했던 비밀번호로 변경할 수 없습니다."),
    IS_DUPLICATE_NICKNAME(HttpStatusCode.BAD_REQUEST, ReasonCode.INVALID_REQUEST, "중복된 닉네임입니다."),

    /* 401 UNAUTHORIZED */
    INVALID_USERNAME_OR_PASSWORD(HttpStatusCode.UNAUTHORIZED, ReasonCode.MISSING_OR_INVALID_AUTHENTICATION_CREDENTIALS, "아이디 혹은 비밀번호가 적합하지 않습니다."),

    /* 404 NOT_FOUND */
    USER_NOT_FOUND(HttpStatusCode.NOT_FOUND, ReasonCode.REQUESTED_RESOURCE_NOT_FOUND, "사용자를 찾을 수 없습니다.");

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
