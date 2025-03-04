package io.devtab.popspot.global.security.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.devtab.popspot.global.security.jwt.filter.JwtAuthenticationFilter;
import io.devtab.popspot.global.security.jwt.filter.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JwtSecurityAdapterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void configure(HttpSecurity httpSecurity) {
        httpSecurity.authenticationProvider(daoAuthenticationProvider);
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
    }
}
