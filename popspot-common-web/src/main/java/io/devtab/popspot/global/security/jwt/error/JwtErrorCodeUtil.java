package io.devtab.popspot.global.security.jwt.error;

import java.util.Map;
import java.util.Optional;

import io.devtab.popspot.global.security.jwt.exception.JwtErrorException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtErrorCodeUtil {
    private static final Map<Class<? extends Exception>, JwtErrorCode> ERROR_CODE_MAP = Map.of(
        ExpiredJwtException.class, JwtErrorCode.EXPIRED_TOKEN,
        MalformedJwtException.class, JwtErrorCode.MALFORMED_TOKEN,
        SignatureException.class, JwtErrorCode.TAMPERED_TOKEN,
        UnsupportedJwtException.class, JwtErrorCode.UNSUPPORTED_JWT_TOKEN
    );

    /**
     * 예외에 해당하는 오류 코드를 반환하거나 기본 오류 코드를 반환합니다.
     *
     * @param exception        {@link Exception} : 발생한 예외
     * @param defaultErrorCode {@link JwtErrorCode} : 기본 오류 코드
     * @return {@link JwtErrorException}
     */
    public static JwtErrorCode determineErrorCode(Exception exception, JwtErrorCode defaultErrorCode) {
        if (exception instanceof JwtErrorException jwtErrorException)
            return jwtErrorException.getErrorCode();

        Class<? extends Exception> exceptionClass = exception.getClass();
        return ERROR_CODE_MAP.getOrDefault(exceptionClass, defaultErrorCode);
    }

    /**
     * 예외에 해당하는 {@link JwtErrorException}을 반환합니다.
     * 기본 오류 코드는 400 UNEXPECTED_ERROR 입니다.
     * 해당 메서드는 {@link #determineErrorCode(Exception, JwtErrorCode)} 메서드를 사용합니다.
     *
     * @param exception {@link Exception} : 발생한 예외
     * @return {@link JwtErrorException}
     */
    public static JwtErrorException determineAuthErrorException(Exception exception) {
        return findAuthErrorException(exception).orElseGet(
            () -> {
                JwtErrorCode authErrorCode = determineErrorCode(exception, JwtErrorCode.UNEXPECTED_ERROR);
                return new JwtErrorException(authErrorCode);
            }
        );
    }

    private static Optional<JwtErrorException> findAuthErrorException(Exception exception) {
        if (exception instanceof JwtErrorException) {
            return Optional.of((JwtErrorException) exception);
        } else if (exception.getCause() instanceof JwtErrorException) {
            return Optional.of((JwtErrorException) exception.getCause());
        }
        return Optional.empty();
    }
}
