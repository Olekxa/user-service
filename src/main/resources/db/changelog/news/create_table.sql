--liquibase formatted sql
--changeset greedobank:create-tables

CREATE TABLE news
(
    news_id INT PRIMARY KEY(),
    display_on_site BOOLEAN,
    send_by_email BOOLEAN,
    title VARCHAR(NOT NULL),
    description VARCHAR(NOT NULL),
    OffsetDateTime TIMESTAMP WITH TIME ZONE(NOT NULL),
    active BOOLEAN,
    createdAt TIMESTAMP WITH TIME ZONE,
    updatedAt TIMESTAMP WITH TIME ZONE
);