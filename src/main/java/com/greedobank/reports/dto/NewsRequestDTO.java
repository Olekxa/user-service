package com.greedobank.reports.dto;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record NewsRequestDTO(
        @NotNull
        Boolean displayOnSite,
        @NotNull
        Boolean sendByEmail,
        @NotNull
        ContentRequestDTO content,
        @NotNull
        OffsetDateTime publicationDate,
        @NotNull
        Boolean active) {
}