package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDao;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.mapper.NewsMapper;
import com.greedobank.reports.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@Configuration
public class ServiceNews {

    @Autowired
    private final NewsDao newsDao;
    private final NewsMapper newsMapper;

    @Autowired
    public ServiceNews(NewsDao newsDao, NewsMapper newsMapper) {
        this.newsDao = newsDao;
        this.newsMapper = newsMapper;
    }

    public NewsResponseDTO create(NewsRequestDTO request) {
        OffsetDateTime timeStamp = OffsetDateTime.now();
        News news = newsMapper.toNewsFromRequest(request);
        news.setCreatedAt(timeStamp);
        news.setUpdatedAt(timeStamp);
        newsDao.save(news);
        return newsMapper.toResponseFromNews(news);
    }
}
