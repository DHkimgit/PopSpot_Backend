package io.devtab.popspot.global.security;

import static io.devtab.popspot.global.security.WebSecurityUrls.*;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfigurationSource;

import io.devtab.popspot.global.security.jwt.config.JwtSecurityAdapterConfig;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtSecurityAdapterConfig securityAdapterConfig;
    private final CorsConfigurationSource corsConfigurationSource;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 방식(예: 로그인 창이 브라우저에 팝업으로 뜨는 방식)을 설정
            .csrf(AbstractHttpConfigurer::disable) // CSRF(크로스 사이트 요청 위조) 보안 기능 설정
            .sessionManagement((session) ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // REST API 서버 + jwt 인증 방식이므로 사용자 정보를 세션으로 기억하지 않음
            .cors((cors) -> cors.configurationSource(corsConfigurationSource)) // 별도의 설정 클래스에서 빈으로 등록된 CORS(교차 출처 리소스 공유) 보안 설정
            .formLogin(AbstractHttpConfigurer::disable) // 스프링이 기본으로 제공하는 로그인 폼(HTML 페이지)을 띄우는 기능
            .logout(AbstractHttpConfigurer::disable) // 로그아웃 기능을 설정. 세션을 사용하지 않는 STATELESS 방식에선 사용되지 않음.
            .with(securityAdapterConfig, Customizer.withDefaults())
            .exceptionHandling(
                exception -> exception
                    .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(authenticationEntryPoint)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(SWAGGER_ENDPOINTS).permitAll()
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.GET, READ_ONLY_PUBLIC_ENDPOINTS).permitAll()
                .requestMatchers(ANONYMOUS_ENDPOINTS).anonymous()
                .requestMatchers(AUTHENTICATED_ENDPOINTS).authenticated()
                .anyRequest().authenticated()
            ).build();
    }
}
