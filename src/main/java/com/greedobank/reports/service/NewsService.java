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

    public NewsResponseDTO get(long id) {
        return newsDAO.findById(id)
                .map(newsMapper::toNewsResponseDTO)
                .orElseThrow(() -> new NotFoundException(String.format("News with id %d was not found", id)));
    }

    public void delete(Long id) {
        News news = newsDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("News with id " + id + " was not found"));
        newsDAO.delete(news);
    }

    public void patch(Long id, NewsRequestDTO request) {
        News news = newsDAO
                .findById(id)
                .orElseThrow(() -> new NotFoundException("News with id " + id + " not found"));
        if (request.displayOnSite() != null) {
            news.setDisplayOnSite(request.displayOnSite());
        }
        if (request.sendByEmail() != null) {
            news.setSendByEmail(request.sendByEmail());
        }
        if (request.content().title() != null) {
            news.setTitle(request.content().title());
        }
        if (request.content().description() != null) {
            news.setDescription(request.content().description());
        }
        if (request.active() != null) {
            news.setActive(request.active());
        }
        news.setUpdatedAt(OffsetDateTime.now());
        newsDAO.save(news);
    }
}
