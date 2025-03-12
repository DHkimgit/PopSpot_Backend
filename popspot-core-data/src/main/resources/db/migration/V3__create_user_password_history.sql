CREATE TABLE user_password_history
(
    id INT UNSIGNED auto_increment primary key,
    user_id INT UNSIGNED NOT NULL,
    password TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_id (user_id)
)
    collate = utf8_unicode_ci;
