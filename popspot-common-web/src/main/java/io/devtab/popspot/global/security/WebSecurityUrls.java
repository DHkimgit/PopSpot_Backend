package io.devtab.popspot.global.security;

public final class WebSecurityUrls {
    public static final String[] READ_ONLY_PUBLIC_ENDPOINTS = {"/favicon.ico"};
    public static final String[] PUBLIC_ENDPOINTS = {};
    public static final String[] ANONYMOUS_ENDPOINTS = {"/auth/**"};
    public static final String[] AUTHENTICATED_ENDPOINTS = {"/admin", "/user/me/**"};
    public static final String[] SWAGGER_ENDPOINTS = {"/api-docs/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger",};
}
