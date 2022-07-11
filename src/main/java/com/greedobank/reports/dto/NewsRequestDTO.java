package com.greedobank.reports.dto;


import java.time.OffsetDateTime;


public record NewsRequestDTO(
        boolean displayOnSite,
        boolean sendByEmail,
        ContentRequestDTO content,
        OffsetDateTime publicationDate,
        boolean active) {
}