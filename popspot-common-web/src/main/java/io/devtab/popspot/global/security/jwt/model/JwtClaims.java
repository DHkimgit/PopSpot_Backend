package io.devtab.popspot.global.security.jwt.model;

import java.util.Map;

public interface JwtClaims {

    Map<String, ?> getClaims();
}
