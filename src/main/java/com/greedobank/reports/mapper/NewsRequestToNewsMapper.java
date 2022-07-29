package com.greedobank.reports.mapper;

import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.model.News;
import org.springframework.stereotype.Component;

@Component
public class NewsRequestToNewsMapper {
    public News toNewsFromRequest(NewsRequestDTO request) {
        News news = new News();
        news.setDisplayOnSite(request.displayOnSite());
        news.setSendByEmail(request.sendByEmail());
        news.setTitle(request.content().title());
        news.setDescription(request.content().description());
        news.setActive(request.active());
        return news;
    }
}
