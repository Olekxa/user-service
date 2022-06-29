package com.javainternship.reportservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {
    @GetMapping("/GET")
    public String index() {
        return "GreedoBank completed Migration to Cloud!";
    }
}
