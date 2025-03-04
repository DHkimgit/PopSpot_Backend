package io.devtab.popspot.global.security.jwt.refresh;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@RedisHash("refreshToken")
@Getter
@ToString(of = {"userId", "token", "ttl"})
@EqualsAndHashCode(of = {"userId", "token"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenEntity {

    @Id
    private String id;
    private Integer userId;
    private long ttl;
    private String token;

    @Builder
    private RefreshTokenEntity(Integer userId, String token, long ttl) {
        this.id = String.valueOf(userId);
        this.userId = userId;
        this.token = token;
        this.ttl = ttl;
    }

    public static RefreshTokenEntity of(Integer userId, String token, long ttl) {
        return RefreshTokenEntity.builder()
            .userId(userId)
            .token(token)
            .ttl(ttl)
            .build();
    }

    public void rotate(String token) {
        this.token = token;
    }
}
