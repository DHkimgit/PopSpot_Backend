package io.devtab.popspot.global.security.jwt.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.devtab.popspot.global.security.jwt.JwtProvider;
import io.devtab.popspot.global.security.jwt.access.AccessTokenClaimKeys;
import io.devtab.popspot.global.security.jwt.error.JwtErrorCode;
import io.devtab.popspot.global.security.jwt.forbidden.ForbiddenTokenService;
import io.devtab.popspot.global.security.jwt.model.JwtClaims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final ForbiddenTokenService forbiddenTokenService;
    private final JwtProvider accessTokenProvider;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (isAnonymousRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = resolveAccessToken(request, response);
        UserDetails userDetails = getUserDetails(accessToken);
        authenticateUser(userDetails, request);
        filterChain.doFilter(request, response);
    }

    private String resolveAccessToken(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String token = accessTokenProvider.resolveToken(authHeader);

        if (!StringUtils.hasText(token)) {
            handleAuthException(JwtErrorCode.EMPTY_ACCESS_TOKEN);
        }

        if (forbiddenTokenService.checkForbidden(token)) {
            handleAuthException(JwtErrorCode.FORBIDDEN_ACCESS_TOKEN);
        }

        if (accessTokenProvider.isTokenExpired(token)) {
            handleAuthException(JwtErrorCode.EXPIRED_TOKEN);
        }

        return token;
    }

    // AccessToken과 RefreshToken이 모두 없는 경우, 익명 사용자로 간주
    private boolean isAnonymousRequest(HttpServletRequest request) {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        return accessToken == null;
    }

    private void handleAuthException(JwtErrorCode errorCode) throws ServletException {
        JwtErrorException exception = new JwtErrorException(errorCode);
        throw new ServletException(exception);
    }

    /**
     * db에서 사용자 엔티티 가져와서 SecurityUser로 변환 후 반환
     */
    private UserDetails getUserDetails(String accessToken) {
        JwtClaims claims = accessTokenProvider.getJwtClaimsFromToken(accessToken);
        String userId = (String) claims.getClaims().get(AccessTokenClaimKeys.USER_ID.getValue());
        log.debug("User ID: {}", userId);

        return userDetailsService.loadUserByUsername(userId);
    }

    /**
     * SecurityContextHolder에 SecurityUser를 등록
     */
    private void authenticateUser(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Authenticated user: {}", userDetails.getUsername());
    }
}
