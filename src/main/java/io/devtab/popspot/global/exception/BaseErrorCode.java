package io.devtab.popspot.global.exception;

public interface BaseErrorCode {
    ErrorCausedBy causedBy();
    String getExplainError() throws NoSuchFieldError;
}
