package com.greedobank.reports.controller;

import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.news.NewsPublic;
import com.greedobank.reports.news.NewsDTO;
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
    public NewsDTO createNews(@RequestBody NewsRequestDTO request) {
        return newsPublic.postNews(request);
    }

    @GetMapping("/api/v1/news")
    public String getAllNews() {
        return "GreedoBank completed Migration to Cloud!";
    }

}

