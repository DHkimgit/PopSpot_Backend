package io.devtab.popspot.global.security.jwt.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

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
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(
        HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws
        IOException,
        ServletException {
        log.warn("handle error: {}", accessDeniedException.getMessage());
        ErrorCausedBy causedBy = ErrorCausedBy.of(HttpStatusCode.FORBIDDEN, ReasonCode.ACCESS_TO_THE_REQUESTED_RESOURCE_IS_FORBIDDEN);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(causedBy.getIntegerCode());
        ErrorResponse errorResponse = ErrorResponse.of(causedBy.getCode(), causedBy.getReason());
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
