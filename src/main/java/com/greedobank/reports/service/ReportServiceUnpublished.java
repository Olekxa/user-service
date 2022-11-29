package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDAO;
import com.greedobank.reports.model.News;
import com.greedobank.reports.utils.StylesGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ReportServiceUnpublished extends ReportServiceBase {
    private final NewsDAO newsDAO;

    @Autowired
    public ReportServiceUnpublished(StylesGenerator stylesGenerator, NewsDAO newsDAO) {
        super(stylesGenerator);
        this.newsDAO = newsDAO;
    }

    @Override
    public List<News> getExpectedNews() {
        return newsDAO.findAllExpectedNews(OffsetDateTime.now());
    }
}
