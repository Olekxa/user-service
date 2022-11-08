package com.greedobank.reports.controller;

import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.dto.NewsUpdateDTO;
import com.greedobank.reports.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping("/api/v1/news")
    @ResponseBody
    @Operation(summary = "Create news", description = "Create news")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public NewsResponseDTO create(@Valid @RequestBody NewsRequestDTO request) {
        return newsService.create(request);
    }

    @GetMapping(value = "/api/v1/news/{id}")
    @ResponseBody
    @Operation(summary = "Getting news", description = "get news by id")
    @PreAuthorize("isAuthenticated()")
    public NewsResponseDTO get(@PathVariable long id) {
        return newsService.get(id);
    }

    @PatchMapping(value = "/api/v1/news/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update news", description = "update news by id if present")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void patch(
            @PathVariable(value = "id") long id,
            @RequestBody NewsUpdateDTO updateDTO) {
        newsService.update(id, updateDTO);
    }

    @DeleteMapping(value = "/api/v1/news/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete news", description = "delete news by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable long id) {
        newsService.delete(id);
    }

    @GetMapping(value = "/api/v1/news")
    @PreAuthorize("isAuthenticated()")
    public String getAllNews() {
        return "GreedoBank completed Migration to Cloud!";
    }
}


