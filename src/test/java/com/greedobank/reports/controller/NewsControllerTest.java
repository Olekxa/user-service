package com.greedobank.reports.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(NewsController.class)
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn400WhenSendingRequestToController() throws Exception {
        mockMvc.perform(get("/api/v1/news"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    public void shouldReturn200WhenSendingNewNews() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content("{\"id\":0,\"displayOnSite\":true,\"sendByEmail\":true,\"content\":{\"title\":\"title\",\"description\":\"last after fail\"},\"active\":true,\"publicationDate\":\"22-10-11\",\"createdAt\":\"2022-07-04T18:58:44Z\",\"updatedAt\":\"2022-07-04T18:58:44Z\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.displayOnSite").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sendByEmail").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true));
    }

    @Test
    public void shouldReturn400withNullField() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content("{\"id\":3,\"displayOnSite\":true,\"sendByEmail\":true,\"content\":{\"title\":\"title\",\"description\":},\"active\":true,\"publicationDate\":\"22-10-11\",\"createdAt\":\"2022-07-04T21:58:44+03:00\",\"updatedAt\":\"2022-07-04T21:58:44+03:00\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}