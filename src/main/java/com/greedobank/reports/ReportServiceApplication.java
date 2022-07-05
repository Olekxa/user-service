package com.greedobank.reports;

import com.greedobank.reports.news.Content;
import com.greedobank.reports.news.News;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.OffsetDateTime;

@SpringBootApplication
public class ReportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportServiceApplication.class, args);
		News news = new News(3,true,true, new Content("title", "last after fail"),true,"22-10-11", OffsetDateTime.now(), OffsetDateTime.now());
		System.out.println(news.toString());
	}
}
