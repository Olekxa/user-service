package com.greedobank.reports.controller;

import com.RestControllerTestConfig;
import com.greedobank.reports.exception.NotFoundException;
import com.greedobank.reports.service.NewsService;
import com.greedobank.reports.service.ReportService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration
@WebMvcTest(NewsController.class)
@Import(RestControllerTestConfig.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    @WithMockUser(username = "dzhmur@griddynamics.com", roles = "ADMIN")
    void serveFile() throws IOException {
        ResponseEntity<File> body = ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.xlsx\"")
                .body(new File("report.xlsx"));
        FileInputStream fileInputStream = new FileInputStream(new File("report.xlsx"));

        Mockito.when(reportService.generateXlsxReport()).thenReturn(fileInputStream.readAllBytes());
    }

    @Test
    void sendReport() {
    }
}