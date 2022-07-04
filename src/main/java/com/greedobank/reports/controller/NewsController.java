package com.greedobank.reports.controller;

import com.greedobank.reports.news.News;
import com.greedobank.reports.news.NewsPublic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {
    private final NewsPublic newsPublic;

    public NewsController() {
        this.newsPublic = new NewsPublic();
    }

    @PostMapping("/api/v1/news")
    public void newNews(@RequestBody News news) {
        newsPublic.postNews(news);
    }

    @GetMapping("/api/v1/news/{id}")
    public String getNews(@PathVariable int id) {
        return newsPublic.getNews(id);
    }

    @GetMapping("/api/v1/news/")
    public String getAllNews() {
        return newsPublic.getAllNews();
    }
}
