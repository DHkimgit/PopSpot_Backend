package io.devtab.popspot.global.security.jwt.filter;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.devtab.popspot.global.exception.ErrorResponse;
import io.devtab.popspot.global.security.jwt.error.JwtErrorCodeUtil;
import io.devtab.popspot.global.security.jwt.exception.JwtErrorException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws
        ServletException,
        IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            JwtErrorException exception = JwtErrorCodeUtil.determineAuthErrorException(e);

            sendAuthError(response, exception);
        }
    }

    private void sendAuthError(HttpServletResponse response, JwtErrorException e) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(e.getErrorCode().getStatusCode().getCode());

        ErrorResponse errorResponse = ErrorResponse.of(e.causedBy().getCode(), e.causedBy().getReason());
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
