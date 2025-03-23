package io.devtab.popspot.global.jwt.refresh;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void save(RefreshTokenEntity refreshToken) {
        refreshTokenRepository.save(refreshToken);
        log.debug("리프레시 토큰 저장 : {}", refreshToken);
    }

    public RefreshTokenEntity refresh(Integer userId, String oldRefreshToken, String newRefreshToken) {
        RefreshTokenEntity refreshToken = findOrElseThrow(userId);
        validateToken(oldRefreshToken, refreshToken);

        refreshToken.rotate(newRefreshToken);
        refreshTokenRepository.save(refreshToken);

        log.info("사용자 {}의 리프레시 토큰 갱신", userId);
        return refreshToken;
    }

    public void deleteAll(Integer userId) {
        refreshTokenRepository.deleteAllByUserId(userId);
        log.info("사용자 {}의 리프레시 토큰 삭제", userId);
    }

    private RefreshTokenEntity findOrElseThrow(Integer userId) {
        return refreshTokenRepository.findById(String.valueOf(userId))
            .orElseThrow(() -> new IllegalArgumentException("refresh token not found"));
    }

    private void validateToken(String requestRefreshToken, RefreshTokenEntity expectedRefreshToken) throws IllegalStateException {
        if(isTakenAway(requestRefreshToken, expectedRefreshToken.getToken())) {
            log.warn("리프레시 토큰 불일치(탈취). expected : {}, actual : {}", requestRefreshToken, expectedRefreshToken.getToken());
            refreshTokenRepository.deleteAllByUserId(expectedRefreshToken.getUserId());
            log.info("사용자 {}의 리프레시 토큰 삭제", expectedRefreshToken.getUserId());

            throw new IllegalStateException("refresh token mismatched");
        }
    }

    private boolean isTakenAway(String requestRefreshToken, String expectedRefreshToken) {
        return !expectedRefreshToken.equals(requestRefreshToken);
    }
}
