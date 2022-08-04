package com.greedobank.reports.mapper;

import com.greedobank.reports.dto.ContentResponseDTO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.model.News;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {
    public News toNews(NewsRequestDTO request) {
        News news = new News();
        news.setDisplayOnSite(request.displayOnSite());
        news.setSendByEmail(request.sendByEmail());
        news.setTitle(request.content().title());
        news.setDescription(request.content().description());
        news.setPublicationDate(request.publicationDate());
        news.setActive(request.active());
        return news;
    }

    public NewsResponseDTO toNewsResponseDTO(News news) {
        return new NewsResponseDTO(
                news.getId(),
                news.isDisplayOnSite(),
                news.isSendByEmail(),
                new ContentResponseDTO(
                        news.getTitle(),
                        news.getDescription()),
                news.getPublicationDate(),
                news.isActive(),
                news.getCreatedAt(),
                news.getUpdatedAt());
    }
}
