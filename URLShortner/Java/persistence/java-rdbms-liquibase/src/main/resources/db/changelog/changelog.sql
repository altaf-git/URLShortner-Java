-- liquibase formatted sql
CREATE SCHEMA IF NOT EXISTS altaf;

CREATE TABLE IF NOT EXISTS altaf.url (uuid UUID,
        longUrl TEXT, shortUrl TEXT, created_by TEXT, created_date TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
        modified_by TEXT NULL, modified_date TIMESTAMPTZ NULL, comments TEXT, is_valid BOOLEAN);