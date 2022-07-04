package com.greedobank.reports.controller;

import com.greedobank.reports.news.News;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(NewsController.class)
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsController newsController;

    @Test
    public void shouldReturn400WhenSendingRequestToController() throws Exception {
        mockMvc.perform(get("/api/v1/news"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(equalTo("")));
    }

//    @Test
//    public void shouldReturn200WhenSendingNewNews(String news) throws Exception {
//        String info = "{\"displayOnSite\": \"true\",\"sendByEmail\": \"true\",\"title\": \"Good news\", \"content\": \"content\", \"active\": \"true\"}";
//        given(this.newsController.getNews(1))
//                .willReturn(String.valueOf(new News(true, true, "Good news", "win", true )));
//    }
}