package com.greedobank.reports.service;

import com.greedobank.reports.dao.NewsDAO;
import com.greedobank.reports.dto.ContentRequestDTO;
import com.greedobank.reports.dto.ContentResponseDTO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.dto.NewsUpdateDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void shouldReturnResponseWhenCreateNews() {
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
    public void shouldReturnResponseWhenGetNewsById() {
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
    public void shouldReturnErrorWhenGetNewsNotFoundByID() {
        String error = "News with id 1 was not found";

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> newsService.get(1L));

        assertEquals(error, notFoundException.getMessage());
    }

    @Test
    public void shouldDeleteNewsByIdSuccess() {
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
        doNothing().when(newsDAO).delete(news);

        newsService.delete(1L);
        verify(newsDAO, times(1)).findById(1L);
        verify(newsDAO, times(1)).delete(news);
    }

    @Test
    public void shouldReturnErrorWhenDeleteNewsById() {
        String error = "News with id 1 was not found";

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> newsService.delete(1L));

        assertEquals(error, notFoundException.getMessage());
        verify(newsDAO, times(1)).findById(1L);
        verify(newsDAO, times(0)).deleteById(1L);
    }

    @Test
    public void shouldChangeNewsWhenUpdateNews() {
        val news = new News(
                1,
                true,
                true,
                "first news",
                "some text",
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                true,
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"));
        NewsUpdateDTO request = new NewsUpdateDTO(
                false,
                false,
                "new title",
                "changed news",
                OffsetDateTime.parse("2023-12-04T21:58:30+00:00"),
                false);

        when(newsDAO.findById(1L)).thenReturn(Optional.of(news));
        newsService.update(1L, request);

        verify(newsDAO, times(1)).findById(1L);
        verify(newsDAO, times(1)).save(news);
    }

    @Test
    public void shouldChangeNewsWhenUpdateWithNullFields() {
        val news = new News(
                1,
                true,
                true,
                "first news",
                "some text",
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                true,
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"));
        NewsUpdateDTO request = new NewsUpdateDTO(
                true,
                null,
                null,
                "some text",
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                true);

        when(newsDAO.findById(1L)).thenReturn(Optional.of(news));
        newsService.update(1L, request);

        verify(newsDAO, times(1)).findById(1L);
        verify(newsDAO, times(1)).save(news);
    }

    @Test
    public void shouldReturnErrorWhenUpdateNewsById() {
        String error = "News with id 1 was not found";
        NewsUpdateDTO request = new NewsUpdateDTO(
                true,
                null,
                null,
                "some text",
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                true);

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> newsService.update(1L, request));
        assertEquals(error, notFoundException.getMessage());
        verify(newsDAO, times(1)).findById(1L);
        verify(newsDAO, times(0)).deleteById(1L);
    }
}