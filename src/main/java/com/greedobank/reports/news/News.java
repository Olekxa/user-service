package com.greedobank.reports.news;

import java.time.OffsetDateTime;

public record News(long id,
                   boolean displayOnSite,
                   boolean sendByEmail,
                   Content content,
                   String publicationDate,
                   boolean active,
                   OffsetDateTime createdAt,
                   OffsetDateTime updatedAt) {
}
