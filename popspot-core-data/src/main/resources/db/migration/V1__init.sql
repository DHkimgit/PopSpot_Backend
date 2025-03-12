CREATE TABLE users
(
    id INT UNSIGNED auto_increment primary key,
    password TEXT NOT NULL,
    name VARCHAR(50) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL
        user_type VARCHAR(255) NOT NULL,
    gender VARCHAR(6) NULL,
    marketing_agree TINYINT(1) NULL default 0,
    is_deleted TINYINT(1) default 0,
    last_logged_at TIMESTAMP NULL,
    registered_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    constraint email_UNIQUE unique(email),
    constraint nickname_UNIQUE unique(nickname)
)
    collate = utf8_unicode_ci;

CREATE TABLE user_deletions
(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNSIGNED NOT NULL,
    reason VARCHAR(255) NOT NULL COMMENT '탈퇴 사유',
    deleted_at TIMESTAMP NOT NULL COMMENT '탈퇴 완료 시점',
    feedback TEXT NULL COMMENT '사용자 피드백',
    ip_address VARCHAR(45) NULL COMMENT '탈퇴 요청 IP 주소 (IPv6 지원)',
    device_info VARCHAR(255) NULL COMMENT '디바이스 정보',
    status VARCHAR(45) COMMENT '탈퇴 처리 상태',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id)
)
    COLLATE = utf8_unicode_ci;
