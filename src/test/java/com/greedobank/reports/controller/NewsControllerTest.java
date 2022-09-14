package com.greedobank.reports.controller;

import com.RestControllerTestConfig;
import com.greedobank.reports.dto.ContentResponseDTO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.dto.NewsUpdateDTO;
import com.greedobank.reports.exception.NotFoundException;
import com.greedobank.reports.service.NewsService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.OffsetDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WebMvcTest(NewsController.class)
@Import(RestControllerTestConfig.class)
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @Test
    @WithMockUser
    public void shouldReturn200WhenSendingRequestToController() throws Exception {
        mockMvc.perform(get("/api/v1/news"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(equalTo("GreedoBank completed Migration to Cloud!")));
    }

    @Test
    @WithMockUser(username = "dzhmur@griddynamics.com", roles = "ADMIN")
    public void shouldReturn200AndResponseWhenSendingNewNews() throws Exception {
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
        NewsResponseDTO responseDTO =
                new NewsResponseDTO(
                        1,
                        true,
                        true,
                        new ContentResponseDTO(
                                "title",
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

        Mockito.when(newsService.create(any(NewsRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    @WithMockUser(username = "dzhmur@griddynamics.com", roles = "USER")
    public void shouldReturn200AndNewsWhenGetById() throws Exception {
        String response = """
                {
                  "id": 1,
                  "displayOnSite": true,
                  "sendByEmail": true,
                  "content": {
                    "title": "title",
                    "description": "some text"
                  },
                  "publicationDate": "2022-07-04T21:58:44+03:00",
                  "active": true,
                  "createdAt": "2022-07-04T21:58:44+03:00",
                  "updatedAt": "2022-07-04T21:58:44+03:00"
                }
                """;
        val news = new NewsResponseDTO(
                1,
                true,
                true,
                new ContentResponseDTO(
                        "title",
                        "some text"),
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                true,
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"));

        Mockito.when(newsService.get(1L)).thenReturn(news);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    @WithMockUser(username = "dzhmur@griddynamics.com", roles = "ADMIN")
    public void shouldReturn404WhenNewsNotFoundById() throws Exception {
        String response = """
                {
                  "reason": "News with id 2 not found"
                }
                """;

        Mockito.when(newsService.get(2L)).thenThrow(new NotFoundException("News with id 2 not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news/{id}", 2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(response));
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldReturn204WhenUpdateNews() throws Exception {
        String updateRequest = """
                {
                  "displayOnSite": true,
                  "sendByEmail": true,
                  "title": "change first",
                  "description": "string",
                  "publicationDate": "2022-09-05T10:27:59.758Z",
                  "active": true
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/news/{id}", 1, updateRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(updateRequest)
                        .with(csrf()))
                .andExpect(status().isNoContent());
        verify(newsService, times(1)).update(any(Long.class), any(NewsUpdateDTO.class));
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldReturn404WhenUpdateNewsNotFoundById() throws Exception {
        String requestJson = """
                {
                   "displayOnSite": true,
                   "sendByEmail": true,
                   "title": "change first",
                   "description": "string",
                   "publicationDate": "2022-09-05T10:27:59.758Z",
                   "active": true
                 }
                """;
        String error = """
                {
                  "reason": "News with id 2 was not found"
                }
                """;

        Mockito.doThrow(new NotFoundException("News with id 2 was not found"))
                .when(newsService)
                .update(any(Long.class), any(NewsUpdateDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/news/{id}", 2L)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(content().json(error));
        verify(newsService, times(1)).update(eq(2L), any(NewsUpdateDTO.class));
    }

    @Test
    @WithMockUser(username = "okukurik@griddynamics.com", roles = "ADMIN")
    public void shouldReturn204WhenDeleteNewsById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/news/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldReturn404WhenDeleteNewsNotFoundById() throws Exception {
        String error = """
                {
                  "reason": "News with id 2 was not found"
                }
                """;

        doThrow(new NotFoundException("News with id 2 was not found")).when(newsService).delete(2L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/news/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(content().json(error));
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldReturn400WhenPostRequestWithInvalidFieldDisplayOnSite() throws Exception {
        String request = """
                {
                  "sendByEmail": true,
                  "content": {
                    "title": "string",
                    "description": "string"
                  },
                  "publicationDate": "2022-08-09T15:41:11.774Z",
                  "active": true
                }
                """;
        String error = """
                 {
                   "reason": "Incorrect request",
                   "details": [
                     "DisplayOnSite can't be null"
                   ]
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(error));
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldReturn400WhenPostRequestWithInvalidFieldSendByEmail() throws Exception {
        String request = """
                {
                  "displayOnSite": true,
                  "content": {
                    "title": "string",
                    "description": "string"
                  },
                  "publicationDate": "2022-08-09T17:20:36.174Z",
                  "active": true
                }
                """;
        String error = """
                {
                    "reason": "Incorrect request",
                    "details": [
                      "SendByEmail can't be null"
                    ]
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(error));
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldReturn400WhenPostRequestWithInvalidContent() throws Exception {
        String request = """
                {
                  "displayOnSite": true,
                  "sendByEmail": true,
                  "publicationDate": "2022-08-09T17:25:40.890Z",
                  "active": true
                }
                """;
        String error = """
                {
                    "reason": "Incorrect request",
                    "details": [
                      "Content can't be null"
                    ]
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).
                        with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(error));
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldReturn400WhenPostRequestWithInvalidTitle() throws Exception {
        String request = """
                {
                  "displayOnSite": true,
                  "sendByEmail": true,
                  "content": {
                    "description": "string"
                  },
                  "publicationDate": "2022-08-09T17:30:06.560Z",
                  "active": true
                }
                """;
        String error = """
                {
                    "reason": "Incorrect request",
                    "details": [
                      "Title can't be empty or null"
                    ]
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(error));
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldReturn400WhenPostRequestWithInvalidDescription() throws Exception {
        String request = """
                {
                  "displayOnSite": true,
                  "sendByEmail": true,
                  "content": {
                    "title": "string"
                  },
                  "publicationDate": "2022-08-09T17:31:59.675Z",
                  "active": true
                }
                """;
        String error = """
                {
                    "reason": "Incorrect request",
                    "details": [
                      "Description can't be empty or null"
                    ]
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(error));
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldReturn400WhenPostRequestWithInvalidPublicationDate() throws Exception {
        String request = """
                {
                  "displayOnSite": true,
                  "sendByEmail": true,
                  "content": {
                    "title": "string",
                    "description": "string"
                  },
                  "active": true
                }
                """;
        String error = """
                {
                  "reason": "Incorrect request",
                  "details": [
                    "PublicationDate can't be null"
                  ]
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(error));
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldReturn400WhenPostRequestWithInvalidActive() throws Exception {
        String request = """
                {
                  "displayOnSite": true,
                  "sendByEmail": true,
                  "content": {
                    "title": "string",
                    "description": "string"
                  },
                  "publicationDate": "2022-08-15T07:37:40.159Z"
                }
                """;
        String error = """
                {
                  "reason": "Incorrect request",
                  "details": [
                    "Active can't be null"
                  ]
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(error));
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldReturn400WhenPostRequestWithAllEmptyLines() throws Exception {
        String request = """
                {
                                
                }
                """;
        String error = """
                {
                   "reason": "Incorrect request",
                   "details": [
                     "Content can't be null",
                     "PublicationDate can't be null",
                     "SendByEmail can't be null",
                     "DisplayOnSite can't be null",
                     "Active can't be null"
                   ]
                 }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(error));
    }

    @Test
    @WithMockUser(username = "john", roles = {"ADMIN"})
    public void shouldReturn404WhenSendIncorrectPath() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news/get/path")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn401WhenUnauthorizedUserUseGet() throws Exception {
        mockMvc.perform(get("/api/v1/news/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "okukurik@griddynamics.com", roles = "USER")
    public void shouldReturn403UserSendingNewNewsWithMissingPrivilege() throws Exception {
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

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(username = "okukurik@griddynamics.com", roles = "USER")
    public void shouldReturn403WhenDeleteNewsByIdWithMissingPrivilege() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/news/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "okukurik@griddynamics.com", roles = "USER")
    public void shouldReturn403WhenUpdateNewsWithMissingPrivilege() throws Exception {
        String updateRequest = """
                {
                  "displayOnSite": true,
                  "sendByEmail": true,
                  "title": "change first",
                  "description": "string",
                  "publicationDate": "2022-09-05T10:27:59.758Z",
                  "active": true
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/news/{id}", 1, updateRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(updateRequest)
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

}
