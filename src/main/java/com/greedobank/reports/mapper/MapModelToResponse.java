package com.greedobank.reports.mapper;

import com.greedobank.reports.dto.ContentResponseDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.model.Model;

public class MapModelToResponse {
    public NewsResponseDTO mapModelToResponse(Model request) {
        return new NewsResponseDTO(
                request.getId(),
                request.isDisplayOnSite(),
                request.isSendByEmail(),
                new ContentResponseDTO(
                        request.getTitle(),
                        request.getDescription()),
                request.getPublicationDate(),
                request.isActive(),
                request.getCreatedAt(),
                request.getUpdatedAt());
    }
}
