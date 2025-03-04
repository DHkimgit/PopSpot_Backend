package io.devtab.popspot.global.security.jwt.util;

import java.util.function.Function;

import io.devtab.popspot.global.security.jwt.model.JwtClaims;

public class JwtClaimsParserUtil {

    @SuppressWarnings("unchecked")
    public static <T> T getClaimsValue(JwtClaims claims, String key, Class<T> type) {
        Object value = claims.getClaims().get(key);
        if (value != null && type.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return null;
    }

    public static <T> T getClaimsValue(JwtClaims claims, String key, Function<String, T> valueConverter) {
        Object value = claims.getClaims().get(key);
        if (value != null) {
            return valueConverter.apply((String) value);
        }
        return null;
    }
}
