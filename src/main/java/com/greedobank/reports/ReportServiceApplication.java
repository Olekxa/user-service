package com.greedobank.reports;

import com.greedobank.reports.dto.ContentRequestDTO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.news.NewsPublic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.OffsetDateTime;

@SpringBootApplication
public class ReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportServiceApplication.class, args);

        NewsPublic zero = new NewsPublic();
        System.out.println(zero.postNews(new NewsRequestDTO(true,
                true, new ContentRequestDTO("title",
                "last after fail"),
                OffsetDateTime.parse("2022-07-04T21:58:44+03:00"),
                true)));
    }
}
