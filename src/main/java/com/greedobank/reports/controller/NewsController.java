package com.greedobank.reports.controller;

import com.greedobank.reports.dto.ContentResponseDTO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;


@RestController
public class NewsController {


    @PostMapping("/api/v1/news")
    @ResponseBody
    public NewsResponseDTO createNews(@RequestBody NewsRequestDTO request) {
        OffsetDateTime timeCreate = OffsetDateTime.parse("2022-07-10T23:34:50.657873+03:00");
        return new NewsResponseDTO(1,
                request.displayOnSite(),
                request.displayOnSite(),
                new ContentResponseDTO(
                        request.content().title(),
                        request.content().description()),
                request.publicationDate(),
                request.active(),
                timeCreate,
                timeCreate);
    }

    @GetMapping("/api/v1/news")
    public String getAllNews() {
        return "GreedoBank completed Migration to Cloud!";
    }

}

