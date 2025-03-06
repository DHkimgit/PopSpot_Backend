package io.devtab.popspot.global.response;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SuccessResponse<T> {

    private final String code = "200";

    private T data;

    @Builder
    public SuccessResponse(T data) {
        this.data = data;
    }

    public static <T> SuccessResponse<T> from(T data) {
        return SuccessResponse.<T>builder()
            .data(data)
            .build();
    }

    public static <V> SuccessResponse<Map<String, V>> from(String key, V data) {
        return SuccessResponse.<Map<String, V>>builder()
            .data(Map.of(key, data))
            .build();
    }

    /**
     * data가 null인 경우 사용한다.
     * <br/>
     * data : {} 형태의 성공 응답을 반환한다.
     */
    public static SuccessResponse<?> noContent() {
        return SuccessResponse.builder().data(Map.of()).build();
    }



}
