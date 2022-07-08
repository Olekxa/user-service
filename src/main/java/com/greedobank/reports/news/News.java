package com.greedobank.reports.news;

public record News(long id,
                   boolean displayOnSite,
                   boolean sendByEmail,
                   Content content,
                   String publicationDate,
                   boolean active,
                   String createdAt,
                   String updatedAt) {
}
