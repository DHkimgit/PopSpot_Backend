package io.devtab.popspot.domain.company.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.devtab.popspot.global.exception.CompanyErrorCode;
import io.devtab.popspot.global.exception.CompanyErrorException;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CompanyAccountInviteCodeRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public CompanyAccountInviteCode save(CompanyAccountInviteCode code) {
        try {
            String messageJson = objectMapper.writeValueAsString(code);
            String key = getAccountInviteCodeRedisKey(code.getCode());

            redisTemplate.opsForValue().set(key, messageJson, code.getExpiration(), TimeUnit.SECONDS);

        } catch (JsonProcessingException e) {
            throw new CompanyErrorException(CompanyErrorCode.ACCOUNT_INVITE_CODE_SAVE_FAIL);
        }
        return code;
    }

    public CompanyAccountInviteCode findByCode(String code) {
        String key = getAccountInviteCodeRedisKey(code);
        String codeJson = redisTemplate.opsForValue().get(key);

        return convertToEntity(codeJson);
    }

    public boolean deleteByCode(String code) {
        String key = getAccountInviteCodeRedisKey(code);
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    private String getAccountInviteCodeRedisKey(String code) {
        return "CompanyAccountInviteCode:" + code;
    }

    private CompanyAccountInviteCode convertToEntity(String code){
        if(code.isBlank() || code.isEmpty()) {
            throw new CompanyErrorException(CompanyErrorCode.ACCOUNT_INVITE_CODE_PARSE_FAIL);
        }

        try {
            return objectMapper.readValue(code, CompanyAccountInviteCode.class);
        } catch (JsonProcessingException e) {
            throw new CompanyErrorException(CompanyErrorCode.ACCOUNT_INVITE_CODE_PARSE_FAIL);
        }
    }
}
