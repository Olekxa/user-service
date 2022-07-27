package com.greedobank.reports.mapper;

import com.greedobank.reports.dto.response.ContentResponseDTO;
import com.greedobank.reports.dto.response.NewsResponseDTO;
import com.greedobank.reports.model.Model;

public class MapModelToResponse {
    public NewsResponseDTO mapModelToResponse(Model request) {
        return new NewsResponseDTO(
                request.getId(),
                request.isDisplayOnSite(),
                request.isSendByEmail(),
                new ContentResponseDTO(
                        request.getContent().getTitle(),
                        request.getContent().getDescription()),
                request.getPublicationDate(),
                request.isActive(),
                request.getCreatedAt(),
                request.getUpdatedAt());
    }
}
