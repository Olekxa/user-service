--liquibase formatted sql
--changeset greedobank:create-tables

CREATE TABLE news
(
    news_id INT PRIMARY KEY,
    displayOnSite BOOLEAN,
    sendByEmail BOOLEAN,
    title VARCHAR,
    description VARCHAR,
    OffsetDateTime TIMESTAMP WITH TIME ZONE,
    active BOOLEAN,
    createdAt TIMESTAMP WITH TIME ZONE,
    updatedAt TIMESTAMP WITH TIME ZONE
);