package io.devtab.popspot.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReasonCode {

    /* 400_BAD_REQUEST */
    // 요청의 구문이 잘못된 경우 (예: JSON 형식이 잘못된 경우)
    INVALID_REQUEST_SYNTAX(0),

    // 필수 매개변수가 누락된 경우 (예: 필수 필드가 요청에 포함되지 않은 경우)
    MISSING_REQUIRED_PARAMETER(1),

    // 매개변수의 형식이 잘못된 경우 (예: 숫자가 아닌 값이 숫자 필드에 입력된 경우)
    MALFORMED_PARAMETER(2),

    // 요청 본문의 형식이 잘못된 경우 (예: JSON 구조가 잘못된 경우)
    MALFORMED_REQUEST_BODY(3),

    // 요청 자체가 유효하지 않은 경우 (예: 잘못된 데이터 조합)
    INVALID_REQUEST(4),

    // 클라이언트 측 오류로 요청을 처리할 수 없는 경우
    CLIENT_ERROR(5),

    /* 401_UNAUTHORIZED */
    // 인증 자격 증명(토큰 등)이 누락되었거나 유효하지 않은 경우
    MISSING_OR_INVALID_AUTHENTICATION_CREDENTIALS(0),

    // 토큰이 만료되었거나 취소된 경우
    EXPIRED_OR_REVOKED_TOKEN(1),

    // 요청한 작업을 수행할 권한이 부족한 경우
    INSUFFICIENT_PERMISSIONS(2),

    // 토큰이 변조되었거나 형식이 잘못된 경우
    TAMPERED_OR_MALFORMED_TOKEN(3),

    // 요청한 리소스의 소유권이 없는 경우
    WITHOUT_OWNERSHIP(4),

    /* 403_FORBIDDEN */
    // 요청한 리소스에 접근할 권한이 없는 경우
    ACCESS_TO_THE_REQUESTED_RESOURCE_IS_FORBIDDEN(0),

    // 요청을 보낸 IP 주소가 차단된 경우
    IP_ADDRESS_BLOCKED(1),

    // 사용자 계정이 정지되거나 차단된 경우
    USER_ACCOUNT_SUSPENDED_OR_BANNED(2),

    // 사용자 역할(Role)에 따라 리소스 접근이 허용되지 않는 경우
    ACCESS_TO_RESOURCE_NOT_ALLOWED_FOR_USER_ROLE(3),

    /* 404_NOT_FOUND */
    // 요청한 리소스가 서버에 존재하지 않는 경우
    REQUESTED_RESOURCE_NOT_FOUND(0),

    // 요청한 URL 또는 엔드포인트가 유효하지 않은 경우
    INVALID_URL_OR_ENDPOINT(1),

    // 리소스가 삭제되었거나 이동된 경우
    RESOURCE_DELETED_OR_MOVED(2),

    /* 405_METHOD_NOT_ALLOWED */
    // 요청한 HTTP 메서드(GET, POST 등)가 지원되지 않는 경우
    REQUEST_METHOD_NOT_SUPPORTED(0),

    // 지원되지 않는 메서드로 접근을 시도한 경우
    ATTEMPTED_TO_ACCESS_UNSUPPORTED_METHOD(1),

    /* 406_NOT_ACCEPTABLE */
    // 요청한 응답 형식(예: XML, JSON)이 서버에서 지원되지 않는 경우
    REQUESTED_RESPONSE_FORMAT_NOT_SUPPORTED(0),

    /* 409_CONFLICT */
    // 요청이 현재 리소스의 상태와 충돌하는 경우 (예: 중복 데이터 생성 시도)
    REQUEST_CONFLICTS_WITH_CURRENT_STATE_OF_RESOURCE(0),

    // 요청한 리소스가 이미 존재하는 경우 (예: 동일한 ID로 리소스 생성 시도)
    RESOURCE_ALREADY_EXISTS(1),

    // 리소스가 동시에 수정되어 충돌이 발생한 경우
    CONCURRENT_MODIFICATION_CONFLICT(2),

    /* 412_PRECONDITION_FAILED */
    // 요청 헤더의 전제 조건이 충족되지 않은 경우
    PRECONDITION_REQUEST_HEADER_NOT_MATCHED(0),

    // If-Match 또는 If-None-Match 헤더 조건이 충족되지 않은 경우
    IF_MATCH_HEADER_OR_IF_NONE_MATCH_HEADER_NOT_MATCHED(1),

    /* 422_UNPROCESSABLE_CONTENT */
    // 요청 본문에 필수 매개변수가 누락된 경우
    REQUIRED_PARAMETERS_MISSING_IN_REQUEST_BODY(0),

    // 요청 본문의 데이터 유효성 검증에 실패한 경우 (예: 이메일 형식이 잘못된 경우)
    VALIDATION_ERROR_IN_REQUEST_BODY(1),

    // 요청 본문의 데이터 타입이 일치하지 않는 경우 (예: 문자열이 숫자 필드에 입력된 경우)
    TYPE_MISMATCH_ERROR_IN_REQUEST_BODY(2),

    /* 500_INTERNAL_SERVER_ERROR */
    // 서버에서 예기치 않은 오류가 발생한 경우
    UNEXPECTED_ERROR(0),
    ;

    private final int code;

    @Override
    public String toString() {
        return name();
    }
}
