package io.devtab.popspot.global.jwt.refresh;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenCustomRepositoryImpl implements RefreshTokenCustomRepository{

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void deleteAllByUserId(Integer userId) {
        String pattern = "refreshToken:" + userId + ":*";
        Set<String> keys = redisTemplate.keys(pattern);

        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
