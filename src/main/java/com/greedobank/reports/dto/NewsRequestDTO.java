package com.greedobank.reports.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record NewsRequestDTO(
        @NotNull(message = "Display_on_site can't be null")
        Boolean displayOnSite,

        @NotNull(message = "Send_by_email can't be null")
        Boolean sendByEmail,

        @NotNull(message = "Content can't be null")
        @Valid
        ContentRequestDTO content,

        @NotNull(message = "Publication_date can't be null")
        OffsetDateTime publicationDate,

        @NotNull(message = "Active can't be null")
        Boolean active) {
}