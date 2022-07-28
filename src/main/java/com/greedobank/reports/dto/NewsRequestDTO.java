package com.greedobank.reports.dto;

import java.time.OffsetDateTime;

public record NewsRequestDTO(
        Boolean displayOnSite,
        Boolean sendByEmail,
        ContentRequestDTO content,
        OffsetDateTime publicationDate,
        Boolean active) {
}