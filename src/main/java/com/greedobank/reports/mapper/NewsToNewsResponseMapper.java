package com.greedobank.reports.mapper;

import com.greedobank.reports.dto.ContentResponseDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.model.News;
import org.springframework.stereotype.Component;

@Component
public class NewsToNewsResponseMapper {
    public NewsResponseDTO toResponseFromNews(News request) {
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
