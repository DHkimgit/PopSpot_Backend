package io.devtab.popspot.global.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private String code;

    private String message;

    private Object fieldErrors;

    @Builder
    private ErrorResponse(String code, String message, Object fieldErrors) {
        this.code = code;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public static ErrorResponse of(String code, String message) {
        return builder()
            .code(code)
            .message(message)
            .build();
    }

    public static ErrorResponse failure(String code, String message, Object fieldErrors) {
        return ErrorResponse.builder()
            .code(code)
            .message(message)
            .fieldErrors(fieldErrors)
            .build();
    }
}

