ALTER TABLE users
    ADD COLUMN password_update_at TIMESTAMP
        DEFAULT CURRENT_TIMESTAMP NOT NULL;
