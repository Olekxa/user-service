package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDAO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.exception.NotFoundException;
import com.greedobank.reports.mapper.NewsMapper;
import com.greedobank.reports.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class NewsService {
    private final NewsDAO newsDAO;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsService(NewsDAO newsDAO, NewsMapper newsMapper) {
        this.newsDAO = newsDAO;
        this.newsMapper = newsMapper;
    }

    public NewsResponseDTO create(NewsRequestDTO request) {
        OffsetDateTime timeStamp = OffsetDateTime.now();
        News news = newsMapper.toNews(request);
        news.setCreatedAt(timeStamp);
        news.setUpdatedAt(timeStamp);
        newsDAO.save(news);
        return newsMapper.toNewsResponseDTO(news);
    }

    public NewsResponseDTO get(Long id) {
        return newsDAO.findById(id)
                .map(newsMapper::toNewsResponseDTO)
                .orElseThrow(() -> new NotFoundException("News with id " + id + " not found"));
    }
}
