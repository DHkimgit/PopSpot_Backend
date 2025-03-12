package io.devtab.popspot.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.devtab.popspot.domain.user.controller.AuthController;
import io.devtab.popspot.domain.user.controller.UserAccountController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class}, basePackageClasses = {AuthController.class, UserAccountController.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalErrorException.class)
    protected ResponseEntity<ErrorResponse> handleGlobalErrorException(GlobalErrorException e) {
        log.warn("handleGlobalErrorException : {}", e.getMessage());
        ErrorResponse response = ErrorResponse.of(e.getBaseErrorCode().causedBy().getCode(), e.getBaseErrorCode().getExplainError());

        return ResponseEntity.status(e.getBaseErrorCode().causedBy().httpStatusCode().getCode()).body(response);
    }
}
