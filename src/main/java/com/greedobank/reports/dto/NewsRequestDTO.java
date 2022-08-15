package com.greedobank.reports.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record NewsRequestDTO(
        @NotNull(message = "DisplayOnSite can't be null")
        Boolean displayOnSite,

        @NotNull(message = "SendByEmail can't be null")
        Boolean sendByEmail,

        @NotNull(message = "Content can't be null")
        @Valid
        ContentRequestDTO content,

        @NotNull(message = "PublicationDate can't be null")
        OffsetDateTime publicationDate,

        @NotNull(message = "Active can't be null")
        Boolean active) {
}