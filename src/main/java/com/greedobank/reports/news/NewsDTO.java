package com.greedobank.reports.news;

import java.time.OffsetDateTime;

public record NewsDTO(long id,
                      boolean displayOnSite,
                      boolean sendByEmail,
                      Content content,
                      boolean active,
                      String publicationDate,
                      OffsetDateTime createdAt,
                      OffsetDateTime updatedAt) {
    @Override
    public String toString() {
        return "{\n" +
                "id: " + id + "\n" +
                "displayOnSite: " + displayOnSite + "\n" +
                "sendByEmail: " + sendByEmail + "\n" +
                content + "\n" +
                "publicationDate: " + publicationDate + "\n" +
                "active: " + active + "\n" +
                "createdAt: " + createdAt + ",\n" +
                "updatedAt: " + updatedAt + ",\n" +
                "}";
    }
}
