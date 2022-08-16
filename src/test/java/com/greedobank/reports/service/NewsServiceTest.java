package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDAO;
import com.greedobank.reports.dto.ContentRequestDTO;
import com.greedobank.reports.dto.ContentResponseDTO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.exception.NotFoundException;
import com.greedobank.reports.mapper.NewsMapper;
import com.greedobank.reports.model.News;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NewsServiceTest {

    @Mock
    private NewsDAO newsDAO;
    @Mock
    private NewsMapper mapper;

    private NewsService newsService;

    @BeforeEach
    private void setUp() {
        MockitoAnnotations.openMocks(this);
        newsService = new NewsService(newsDAO, mapper);
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

        NewsResponseDTO responseDTO = newsService.create(request);
        verify(newsDAO, times(1)).save(news);
        assertEquals(response, responseDTO);
    }

    @Test
    public void getNewsReturnResponse() {
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

        when(newsDAO.findById(1L)).thenReturn(Optional.of(news));
        when(mapper.toNewsResponseDTO(news)).thenReturn(response);

        NewsResponseDTO responseDTO = newsService.get(1L);
        verify(newsDAO, times(1)).findById(1L);
        assertEquals(response, responseDTO);
    }

    @Test
    public void getNewsReturnError() {
        String error = "News with id 1 not found";

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> newsService.get(1L));

        assertEquals(error, notFoundException.getMessage());
    }

    @Test
    public void deleteNewsByIdSuccess() {
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

        when(newsDAO.findById(1L)).thenReturn(Optional.of(news));
        when(mapper.toNewsResponseDTO(news)).thenReturn(response);
        doNothing().when(newsDAO).deleteById(1L);

        newsService.delete(1L);
        verify(newsDAO, times(1)).findById(1L);
        verify(newsDAO, times(1)).deleteById(1L);
    }

    @Test
    public void deleteNewsByIdError() {
        String error = "News with id 1 not found";

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> newsService.delete(1L));

        assertEquals(error, notFoundException.getMessage());
        verify(newsDAO, times(1)).findById(1L);
        verify(newsDAO, times(0)).deleteById(1L);
    }
}