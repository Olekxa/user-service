package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDAO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.dto.NewsUpdateDTO;
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
    private static final String NOT_FOUND_ERROR_MESSAGE_TEMPLATE = "News with id %d was not found";

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
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ERROR_MESSAGE_TEMPLATE, id)));
    }

    public void delete(long id) {
        News news = newsDAO.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ERROR_MESSAGE_TEMPLATE, id)));
        newsDAO.delete(news);
    }

    public void update(long id, NewsUpdateDTO request) {
        News news = newsDAO
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_ERROR_MESSAGE_TEMPLATE, id)));
        if (request.displayOnSite() != null) {
            news.setDisplayOnSite(request.displayOnSite());
        }
        if (request.sendByEmail() != null) {
            news.setSendByEmail(request.sendByEmail());
        }
        if (request.title() != null) {
            news.setTitle(request.title());
        }
        if (request.description() != null) {
            news.setDescription(request.description());
        }
        if (request.active() != null) {
            news.setActive(request.active());
        }
        news.setUpdatedAt(OffsetDateTime.now());
        newsDAO.save(news);
    }
}
