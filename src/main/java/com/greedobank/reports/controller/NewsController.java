package com.greedobank.reports.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {
    @GetMapping("/api/v1/news")
    public String getAllNews() {
        return "GreedoBank completed Migration to Cloud!";
    }
}
