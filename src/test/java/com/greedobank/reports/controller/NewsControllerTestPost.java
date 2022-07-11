package com.greedobank.reports.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NewsController.class)
public class NewsControllerTestPost {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn200WhenSendingNewNews() throws Exception {
        String request = """
                {
                "displayOnSite":true,
                "sendByEmail":true,
                "content":
                {
                "title":"title",
                "description":"last after fail"
                },
                "active":true,
                "publicationDate":"2022-07-04T21:58:44+03:00",
                }
                """;
        String response = """
                 {
                 "id":0, 
                 "displayOnSite": true, 
                 "sendByEmail": true, 
                 "contentRequestDTO":
                {
                 "title":"title", 
                 "description":"last after fail"
                 }, 
                 "publicationDate":"2022-07-04T21:58:44+03:00", 
                 "active":true, 
                 "createdAt":"2022-07-10T23:34:50.657873+03:00", 
                 "updatedAt":"2022-07-10T23:34:50.657873+03:00"
                 }
                                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    public void shouldReturn400withNullField() throws Exception {
        String request = """
                {
                "id":3,
                "displayOnSite":true,
                "sendByEmail":true,
                "content":
                {
                "title":"title",
                "description":
                },
                "active":true,
                "publicationDate":"22-10-11",
                "createdAt":"2022-07-04T21:58:44+03:00",
                "updatedAt":"2022-07-04T21:58:44+03:00"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}