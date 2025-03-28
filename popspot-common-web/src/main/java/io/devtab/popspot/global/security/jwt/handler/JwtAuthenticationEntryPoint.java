package io.devtab.popspot.global.security.jwt.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.devtab.popspot.global.exception.ErrorCausedBy;
import io.devtab.popspot.global.exception.ErrorResponse;
import io.devtab.popspot.global.exception.HttpStatusCode;
import io.devtab.popspot.global.exception.ReasonCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws
        IOException, ServletException {
        log.warn("commence error: {}", authException.getMessage());
        ErrorCausedBy causedBy = ErrorCausedBy.of(HttpStatusCode.UNAUTHORIZED, ReasonCode.MISSING_OR_INVALID_AUTHENTICATION_CREDENTIALS);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(causedBy.getIntegerCode());
        ErrorResponse errorResponse = ErrorResponse.of(causedBy.getCode(), causedBy.getReason());
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
