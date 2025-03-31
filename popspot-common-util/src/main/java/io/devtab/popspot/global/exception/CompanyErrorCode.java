package io.devtab.popspot.global.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CompanyErrorCode implements BaseErrorCode{

    /* 500_INTERNAL_SERVER_ERROR */
    ACCOUNT_INVITE_CODE_SAVE_FAIL(HttpStatusCode.INTERNAL_SERVER_ERROR, ReasonCode.UNEXPECTED_ERROR, "인증 코드 생성 중 오류가 발생했습니다. 관리자에게 문의하세요."),
    ACCOUNT_INVITE_CODE_PARSE_FAIL(HttpStatusCode.INTERNAL_SERVER_ERROR, ReasonCode.UNEXPECTED_ERROR, "인증 코드 분석 중 오류가 발생했습니다. 관리자에게 문의하세요.");

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
