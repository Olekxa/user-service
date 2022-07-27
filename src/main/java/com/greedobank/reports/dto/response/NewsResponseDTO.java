package com.greedobank.reports.dto.response;

import java.time.OffsetDateTime;

public record NewsResponseDTO(
        long id,
        boolean displayOnSite,
        boolean sendByEmail,
        ContentResponseDTO content,
        OffsetDateTime publicationDate,
        boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
