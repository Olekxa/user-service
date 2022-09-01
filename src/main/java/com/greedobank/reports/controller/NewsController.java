package com.greedobank.reports.controller;

import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.dto.NewsUpdateDTO;
import com.greedobank.reports.service.NewsService;
import com.greedobank.reports.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@Validated
public class NewsController {
    private final NewsService newsService;
    private final ReportService reportService;

    @Autowired
    public NewsController(NewsService newsService, ReportService reportService) {
        this.newsService = newsService;
        this.reportService = reportService;
    }

    @PostMapping("/api/v1/news")
    @ResponseBody
    @Operation(summary = "Create news", description = "Create news")
    public NewsResponseDTO create(@Valid @RequestBody NewsRequestDTO request) {
        return newsService.create(request);
    }

    @GetMapping(value = "/api/v1/news/{id}")
    @ResponseBody
    @Operation(summary = "Getting news", description = "get news by id")
    public NewsResponseDTO get(@PathVariable long id) {
        return newsService.get(id);
    }

    @PatchMapping(value = "/api/v1/news/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update news", description = "update news by id if present")
    public void patch(
            @PathVariable(value = "id") long id,
            @RequestBody NewsUpdateDTO updateDTO) {
        newsService.update(id, updateDTO);
    }

    @DeleteMapping(value = "/api/v1/news/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete news", description = "delete news by id")
    public void delete(@PathVariable long id) {
        newsService.delete(id);
    }

    @GetMapping(value = "/api/v1/news")
    public String getAllNews() {
        return "GreedoBank completed Migration to Cloud!";
    }

    @GetMapping("/api/v1/report")
    @ResponseBody
    @Operation(summary = "Create report", description = "Create report")
    public byte[] serveFile() throws IOException {

//        Workbook wb = reportService.generateXlsxReport();
//        try (OutputStream fileOut = new FileOutputStream("workBookWithData.xls")) {
//            wb.write(fileOut);
//        }
//        InputStream inputStream = new FileInputStream((File) wb);
//        inputStream.read();
        Workbook wb = reportService.generateXlsxReport();
        try (OutputStream fileOut = new FileOutputStream("workBookWithData.xls")) {
           wb.write(fileOut);
        } InputStream in = getClass()
                .getResourceAsStream("/Users/okukurik/IdeaProjects/report/workBookWithData.xls");
      return IOUtils.toByteArray(in);
    }
}


