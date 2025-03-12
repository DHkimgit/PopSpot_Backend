package io.devtab.popspot.domain.jwt.forbidden;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ForbiddenTokenService {

    private final ForbiddenTokenRepository forbiddenTokenRepository;

    public void createForbiddenToken(Integer userId, String accessToken, LocalDateTime expiredAt) {
        LocalDateTime now = LocalDateTime.now();
        long timeToLive = Duration.between(now, expiredAt).toSeconds();

        ForbiddenTokenEntity forbiddenToken = ForbiddenTokenEntity.of(accessToken, userId, timeToLive);
        forbiddenTokenRepository.save(forbiddenToken);
    }

    public boolean checkForbidden(String accessToken) {
        return forbiddenTokenRepository.existsById(accessToken);
    }
}
