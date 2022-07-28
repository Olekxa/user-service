package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDAO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.mapper.MapModelToResponse;
import com.greedobank.reports.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ServiceNews implements INewsService {
    private final NewsDAO newsDAO;

    @Autowired
    public ServiceNews(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }

    @Override
    public NewsResponseDTO create(NewsRequestDTO request) {
        MapModelToResponse mapper = new MapModelToResponse();
        OffsetDateTime timeStamp = OffsetDateTime.now();
        Model model = new Model();
        model.setDisplayOnSite(request.displayOnSite());
        model.setSendByEmail(request.sendByEmail());
        model.setTitle(request.content().title());
        model.setDescription(request.content().description());
        model.setActive(request.active());
        model.setCreatedAt(timeStamp);
        model.setUpdatedAt(timeStamp);
        newsDAO.save(model);
        mapper.mapModelToResponse(model);
        return mapper.mapModelToResponse(model);
    }
}
