package io.devtab.popspot.global.security.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.devtab.popspot.global.jwt.forbidden.ForbiddenTokenService;
import io.devtab.popspot.global.security.jwt.filter.JwtAuthenticationFilter;
import io.devtab.popspot.global.security.jwt.filter.JwtExceptionFilter;
import io.devtab.popspot.global.security.jwt.JwtProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtSecurityFilterConfig {

    private final UserDetailsService userDetailServiceImpl;
    private final ForbiddenTokenService forbiddenTokenService;
    private final JwtProvider accessTokenProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public JwtExceptionFilter jwtExceptionFilter() {
        return new JwtExceptionFilter(objectMapper);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthorizationFilter() {
        return new JwtAuthenticationFilter(userDetailServiceImpl, forbiddenTokenService, accessTokenProvider);
    }
}
