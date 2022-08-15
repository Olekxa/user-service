package com.greedobank.reports.mapper;

import com.greedobank.reports.dto.ContentRequestDTO;
import com.greedobank.reports.dto.ContentResponseDTO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;
import com.greedobank.reports.model.News;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NewsMapperTest {

    private NewsMapper mapper;

    @BeforeEach
    private void setUp() {
        mapper = new NewsMapper();
    }

    @Test
    void toNewsFromRequest() {
        NewsRequestDTO request = new NewsRequestDTO(
                true,
                true,
                new ContentRequestDTO(
                        "title",
                        "some text"),
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                true);
        val newsExpected = new News(
                0,
                true,
                true,
                "title",
                "some text",
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                true,
                null,
                null);

        News newsActual = mapper.toNews(request);

        assertTrue(new ReflectionEquals(newsExpected).matches(newsActual));
    }

    @Test
    void toNewsResponseDTO() {
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
        val responseExpected = new NewsResponseDTO(
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

        NewsResponseDTO responseActual = mapper.toNewsResponseDTO(news);

        assertTrue(new ReflectionEquals(responseExpected).matches(responseActual));
    }
}