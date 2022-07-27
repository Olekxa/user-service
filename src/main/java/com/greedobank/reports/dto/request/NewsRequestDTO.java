package com.greedobank.reports.dto.request;

import java.time.OffsetDateTime;

public record NewsRequestDTO(
        Boolean displayOnSite,
        Boolean sendByEmail,
        ContentRequestDTO content,
        OffsetDateTime publicationDate,
        Boolean active) {
}