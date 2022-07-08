package com.greedobank.reports.controller;

import com.greedobank.reports.news.NewsTemplateRequestDTO;
import com.greedobank.reports.news.NewsPublic;
import com.greedobank.reports.news.News;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {
    private final NewsPublic newsPublic;

    public NewsController() {
        this.newsPublic = new NewsPublic();
    }

    @PostMapping("/api/v1/news")
    @ResponseBody
    public News createNews(@RequestBody NewsTemplateRequestDTO newsTemplateRequestDTO) {
        return newsPublic.postNews(newsTemplateRequestDTO);
    }

    @GetMapping("/api/v1/news")
    public String getAllNews() {
        return "GreedoBank completed Migration to Cloud!";
    }
}

