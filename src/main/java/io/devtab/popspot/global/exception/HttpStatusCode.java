package io.devtab.popspot.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HttpStatusCode {

    // 요청이 성공적으로 처리된 경우
    SUCCESS(200),
    // 리소스가 성공적으로 생성된 경우 (예: 새로운 사용자 등록 성공)
    CREATED(201),
    // 요청이 성공적으로 처리되었으나 반환할 콘텐츠가 없는 경우 (예: 삭제 성공)
    NO_CONTENT(204),

    // 요청이 잘못되었거나 구문 오류가 있는 경우 (예: 잘못된 JSON 형식)
    BAD_REQUEST(400),
    // 인증 자격 증명이 없거나 유효하지 않은 경우 (예: 토큰 누락 또는 만료)
    UNAUTHORIZED(401),
    // 요청한 리소스에 접근할 권한이 없는 경우 (예: 권한 부족)
    FORBIDDEN(403),
    // 요청한 리소스가 서버에 존재하지 않는 경우 (예: 잘못된 URL)
    NOT_FOUND(404),
    // 요청한 HTTP 메서드가 지원되지 않는 경우 (예: POST 대신 GET 사용)
    METHOD_NOT_ALLOWED(405),
    // 요청한 응답 형식이 서버에서 지원되지 않는 경우 (예: XML 요청 시 JSON만 지원)
    NOT_ACCEPTABLE(406),
    // 요청이 현재 리소스의 상태와 충돌하는 경우 (예: 중복 데이터 생성 시도)
    CONFLICT(409),
    // 요청 헤더의 전제 조건이 충족되지 않은 경우
    PRECONDITION_FAILED(412),
    // 요청 본문의 데이터가 처리 불가능한 경우 (예: 유효성 검증 실패)
    UNPROCESSABLE_CONTENT(422),

    // 서버에서 예기치 않은 오류가 발생한 경우 (예: 서버 코드 오류)
    INTERNAL_SERVER_ERROR(500),
    // 서버가 일시적으로 사용 불가능한 경우 (예: 서버 유지보수 중)
    SERVICE_UNAVAILABLE(503);

    private final int code;
}
