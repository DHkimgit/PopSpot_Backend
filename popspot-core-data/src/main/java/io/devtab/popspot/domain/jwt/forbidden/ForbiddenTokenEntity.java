package io.devtab.popspot.domain.jwt.forbidden;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@RedisHash("forbiddenToken")
@EqualsAndHashCode(of = {"accessToken", "userId"})
public class ForbiddenTokenEntity {

    @Id
    private final String accessToken;
    private final Integer userId;
    @TimeToLive
    private final long ttl;

    @Builder
    public ForbiddenTokenEntity(String accessToken, Integer userId, long ttl) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.ttl = ttl;
    }

    public static ForbiddenTokenEntity of(String accessToken, Integer userId, long ttl) {
        return new ForbiddenTokenEntity(accessToken, userId, ttl);
    }
}
