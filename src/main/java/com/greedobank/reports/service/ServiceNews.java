package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDAO;
import com.greedobank.reports.dto.request.NewsRequestDTO;
import com.greedobank.reports.dto.response.NewsResponseDTO;
import com.greedobank.reports.mapper.MapModelToResponse;
import com.greedobank.reports.model.Model;
import com.greedobank.reports.model.ModelContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ServiceNews implements INewsService {
    private final NewsDAO DAO;

    @Autowired
    public ServiceNews(NewsDAO DAO) {
        this.DAO = DAO;
    }

    @Override
    public NewsResponseDTO create(NewsRequestDTO request) {
        MapModelToResponse mapper = new MapModelToResponse();
        OffsetDateTime timeStamp = OffsetDateTime.now();
        Model model = new Model();
        model.setDisplayOnSite(request.displayOnSite());
        model.setSendByEmail(request.sendByEmail());
        model.setContent(new ModelContent(request.content().title(), request.content().description()));
        model.setActive(request.active());
        model.setCreatedAt(timeStamp);
        model.setUpdatedAt(timeStamp);
        DAO.save(model);
        mapper.mapModelToResponse(model);
        return mapper.mapModelToResponse(model);
    }

    @Override
    public NewsResponseDTO get(long id) {
        return null;
    }

    @Override
    public void patch(long id, NewsRequestDTO updateDTO) {
    }

    @Override
    public void delete(long id) {
    }
}
