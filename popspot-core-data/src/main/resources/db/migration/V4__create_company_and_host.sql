CREATE TABLE company
(
    id INT UNSIGNED auto_increment primary key,
    name VARCHAR(50) NOT NULL,
    business_registration_number VARCHAR(20) NOT NULL,
    contact_phone VARCHAR(20) NULL,
    contact_email VARCHAR(100) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    is_deleted TINYINT(1) default 0,
    CONSTRAINT business_registration_number_UNIQUE UNIQUE(business_registration_number)
)
    collate = utf8_unicode_ci;

CREATE TABLE employee
(
    id INT UNSIGNED auto_increment primary key,
    user_id INT UNSIGNED NOT NULL,
    company_id INT UNSIGNED NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    is_deleted TINYINT(1) default 0
)
    collate = utf8_unicode_ci;

CREATE INDEX idx_user_id ON employee(user_id);
CREATE INDEX idx_company_id ON employee(company_id);
