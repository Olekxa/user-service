package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDAO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.mapper.NewsRequestToNewsMapper;
import com.greedobank.reports.mapper.NewsToNewsResponseMapper;
import com.greedobank.reports.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ServiceNews {
    private final NewsDAO newsDAO;
    private final NewsToNewsResponseMapper newsToNewsResponseMapper;
    private final NewsRequestToNewsMapper newsRequestToNewsMapper;

    @Autowired
    public ServiceNews(NewsDAO newsDAO, NewsToNewsResponseMapper newsToNewsResponseMapper, NewsRequestToNewsMapper newsRequestToNewsMapper) {
        this.newsDAO = newsDAO;
        this.newsToNewsResponseMapper = newsToNewsResponseMapper;
        this.newsRequestToNewsMapper = newsRequestToNewsMapper;
    }

    public NewsResponseDTO create(NewsRequestDTO request) {
        OffsetDateTime timeStamp = OffsetDateTime.now();
        News news = newsRequestToNewsMapper.toNewsFromRequest(request);
        news.setCreatedAt(timeStamp);
        news.setUpdatedAt(timeStamp);
        newsDAO.save(news);
        return newsToNewsResponseMapper.toResponseFromNews(news);
    }
}
