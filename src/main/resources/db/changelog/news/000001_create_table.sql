--liquibase formatted sql
--changeset greedobank:create-tables

CREATE TABLE news
(
    news_id INT PRIMARY KEY,
    display_on_site BOOLEAN NOT NULL,
    send_by_email BOOLEAN NOT NULL,
    title VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    offset_date_time TIMESTAMP WITH TIME ZONE NOT NULL,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);