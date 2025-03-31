package io.devtab.popspot.domain.company.redis;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import io.devtab.popspot.domain.company.entity.EmployeeAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@RedisHash("CompanyAccountInviteCode")
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAccountInviteCode {

    private static final long EXPIRE_SECOND = TimeUnit.DAYS.toSeconds(1);

    @Id
    private String code;

    private Integer companyId;
    private EmployeeAuthority authority;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    @TimeToLive
    private Long expiration;

    public static CompanyAccountInviteCode createCode(String code, Integer companyId, EmployeeAuthority authority) {
        return CompanyAccountInviteCode.builder()
            .code(code)
            .companyId(companyId)
            .authority(authority)
            .createdAt(LocalDateTime.now())
            .expiredAt(LocalDateTime.now().plusDays(1))
            .expiration(EXPIRE_SECOND)
            .build();
    }
}
