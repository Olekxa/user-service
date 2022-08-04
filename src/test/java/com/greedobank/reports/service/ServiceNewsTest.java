package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDAO;
import com.greedobank.reports.dto.ContentRequestDTO;
import com.greedobank.reports.dto.ContentResponseDTO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.mapper.NewsMapper;
import com.greedobank.reports.model.News;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ServiceNewsTest {

    @Mock
    private NewsDAO newsDAO;
    @Mock
    private NewsMapper mapper;

    private ServiceNews serviceNews;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceNews = new ServiceNews(newsDAO, mapper);
    }

    @Test
    public void createNewsReturnResponse() {
        NewsRequestDTO request = new NewsRequestDTO(
                true,
                true,
                new ContentRequestDTO(
                        "title",
                        "some text"),
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                true);
        val news = new News(
                1,
                true,
                true,
                "title",
                "some text",
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                true,
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"));

        val response = new NewsResponseDTO(
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

        when(mapper.toNews(request)).thenReturn(news);
        when(mapper.toNewsResponseDTO(news)).thenReturn(response);
        NewsResponseDTO responseDTO = serviceNews.create(request);
        verify(newsDAO, times(1)).save(news);
        assertEquals(response, responseDTO);
    }
}