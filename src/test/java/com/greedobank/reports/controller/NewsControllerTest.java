package com.greedobank.reports.controller;

import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.ContentResponseDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.service.ServiceNews;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(NewsController.class)
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceNews serviceNews;

    @Test
    public void shouldReturn200WhenSendingRequestToController() throws Exception {
        mockMvc.perform(get("/api/v1/news"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(equalTo("GreedoBank completed Migration to Cloud!")));
    }

    @Test
    public void shouldReturn200WhenSendingNewNews() throws Exception {
        String request = """
                {
                    "displayOnSite":true,
                    "sendByEmail":true,
                    "content":{
                        "title":"title",
                        "description":"last after fail"
                    },
                    "active":true,
                    "publicationDate":"2022-07-04T21:58:44+03:00"
                }
                """;
        OffsetDateTime timeCreate = OffsetDateTime.parse("2022-07-10T23:34:50.657873+03:00");
        NewsResponseDTO responseDTO = new NewsResponseDTO(1,
                true,
                true,
                new ContentResponseDTO("title",
                        "last after fail"),
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                true,
                timeCreate,
                timeCreate);
        String response = """
                {
                    "id":1,
                    "displayOnSite": true,
                    "sendByEmail": true,
                    "content":{
                        "title":"title",
                        "description":"last after fail"
                    },
                    "publicationDate":"2022-07-04T21:58:44+03:00",
                    "active":true,
                    "createdAt":"2022-07-10T23:34:50.657873+03:00",
                    "updatedAt":"2022-07-10T23:34:50.657873+03:00"
                }
                               """;
        Mockito.when(serviceNews.create(Mockito.any(NewsRequestDTO.class))).thenReturn(responseDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    public void shouldReturn400WhenSendNewsWithNullField() throws Exception {
        String request = """
                {
                    "id":3,
                    "displayOnSite":true,
                    "sendByEmail":true,
                    "content":{
                        "title":"title",
                        "description":
                    },
                    "active":true,
                    "publicationDate":"22-10-11",
                    "createdAt":"2022-07-10T23:34:50.657873+03:00",
                    "updatedAt":"2022-07-10T23:34:50.657873+03:00"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturn200AndNewsById() throws Exception {
        String response = """
                {
                   "id":1,
                   "displayOnSite": true,
                   "sendByEmail": true,
                   "content":{
                       "title":"title",
                       "description":"description"
                   },
                   "publicationDate":"2022-07-04T18:58:44Z",
                   "active":true,
                   "createdAt":"2022-07-10T23:34:50.657873+03:00",
                   "updatedAt":"2022-07-10T23:34:50.657873+03:00"
                }""";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    public void shouldReturn404WhenNewsNotFoundById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found news").getMessage(),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void shouldReturn200WhenUpdateNews() throws Exception {
        String updateRequest = """
                {
                  "displayOnSite": true,
                  "sendByEmail": false,
                  "content": {
                    "title": "new title",
                    "description": "new description"
                  }
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/news/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenUpdateNewsNotFoundById() throws Exception {
        String updateRequest = """
                {
                  "displayOnSite": true,
                  "sendByEmail": false,
                  "content": {
                    "title": "new title",
                    "description": "new description"
                  }
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/news/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(updateRequest))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found news").getMessage(),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void shouldReturn204WhenDeleteNews() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/news/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturn404WhenDeleteNewsNotFoundById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/news/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals(
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found news").getMessage(),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}
