package com.greedobank.reports.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record NewsRequestDTO(
        @NotNull(message = "displayOnSite can't be null")
        Boolean displayOnSite,

        @NotNull(message = "sendByEmail can't be null")
        Boolean sendByEmail,

        @NotNull(message = "Content can't be null")
        @Valid
        ContentRequestDTO content,

        @NotNull(message = "publicationDate can't be null")
        OffsetDateTime publicationDate,

        @NotNull(message = "Active can't be null")
        Boolean active) {
}