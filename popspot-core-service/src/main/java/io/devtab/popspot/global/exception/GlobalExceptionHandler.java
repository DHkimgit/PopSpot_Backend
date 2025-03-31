package io.devtab.popspot.global.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalErrorException.class)
    protected ResponseEntity<ErrorResponse> handleGlobalErrorException(GlobalErrorException e) {
        log.warn("handleGlobalErrorException : {}", e.getMessage());
        ErrorResponse response = ErrorResponse.of(e.getBaseErrorCode().causedBy().getCode(), e.getBaseErrorCode().getExplainError());

        return ResponseEntity.status(e.getBaseErrorCode().causedBy().httpStatusCode().getCode()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException : {}", e.getMessage());

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse response = ErrorResponse.failure(
            String.valueOf(HttpStatusCode.BAD_REQUEST.getCode()),
            HttpStatusCode.BAD_REQUEST.name(),
            fieldErrors
        );

        return ResponseEntity.status(HttpStatusCode.BAD_REQUEST.getCode()).body(response);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidation(HandlerMethodValidationException e) {
        log.warn("HandlerMethodValidationException : {}", e.getMessage());

        String errorMessage = e.getDetailMessageArguments()[0].toString();

        ErrorResponse response = ErrorResponse.of(
            String.valueOf(HttpStatusCode.BAD_REQUEST.getCode()),
            errorMessage
        );

        return ResponseEntity.status(HttpStatusCode.BAD_REQUEST.getCode()).body(response);
    }
}
