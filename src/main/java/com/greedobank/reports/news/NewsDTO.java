package com.greedobank.reports.news;

import com.greedobank.reports.dto.ContentRequestDTO;

import java.time.OffsetDateTime;

public record NewsDTO(long id,
                      boolean displayOnSite,
                      boolean sendByEmail,
                      ContentDTO content,
                      OffsetDateTime publicationDate,
                      boolean active,
                      OffsetDateTime createdAt,
                      OffsetDateTime updatedAt) {
}
