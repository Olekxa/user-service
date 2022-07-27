package com.greedobank.reports.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table
public record NewsResponseDTO(
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        long id,
        boolean displayOnSite,
        boolean sendByEmail,
        ContentResponseDTO content,
        OffsetDateTime publicationDate,
        boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
