package io.devtab.popspot.global.util;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieUtil {

    public Optional<Cookie> getCookieFromRequest(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
            .filter(cookie -> cookieName.equals(cookie.getName()))
            .findAny();
    }

    public ResponseCookie createCookie(String cookieName, String value, long maxAge) {
        return ResponseCookie.from(cookieName, value)
            .path("/")
            .httpOnly(true)
            .maxAge(maxAge)
            .secure(true)
            .sameSite("None")
            .build();
    }

    public ResponseCookie deleteCookie(String cookieName) {
        return createCookie(cookieName, "", 0);
    }
}
