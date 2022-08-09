package com.greedobank.reports.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record NewsRequestDTO(
        @NotNull(message = "display_on_site can't be null")
        Boolean displayOnSite,

        @NotNull(message = "send_by_email can't be null")
        Boolean sendByEmail,

        @NotNull(message = "content can't be null")
        @Valid
        ContentRequestDTO content,

        @NotNull(message = "publication_date can't be null")
        OffsetDateTime publicationDate,

        @NotNull(message = "active can't be null")
        Boolean active) {
}