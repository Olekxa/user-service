package com.greedobank.reports.dto;

import java.time.OffsetDateTime;

public record NewsUpdateDTO(
        Boolean displayOnSite,
        Boolean sendByEmail,
        String title,
        String description,
        OffsetDateTime publicationDate,
        Boolean active) {
}
