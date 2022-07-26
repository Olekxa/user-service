--liquibase formatted sql
--changeset greedobank:create-tables

CREATE TABLE news
(
    news_id INT PRIMARY KEY,
    display_on_site BOOLEAN,
    send_by_email BOOLEAN,
    title VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    offset_date_time TIMESTAMP WITH TIME ZONE NOT NULL,
    active BOOLEAN,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);