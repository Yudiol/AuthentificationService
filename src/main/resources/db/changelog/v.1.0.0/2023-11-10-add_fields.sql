ALTER TABLE users
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT FALSE;
alter table users
    add column active_code character(100);
