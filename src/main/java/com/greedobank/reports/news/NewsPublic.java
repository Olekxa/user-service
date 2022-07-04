package com.greedobank.reports.news;

import com.greedobank.reports.exeptions.NewsException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Service
public class NewsPublic {
    private List<News> newsPublic = new ArrayList<>();

    public News postNews(News news) {
        if (news.getContent().getContent() == null || news.getContent().getTitle() == null) {
            throw new NewsException("content & title can't be empty");
        }
        if (newsPublic.stream().noneMatch(x -> x.getContent().equals(news.getContent()))) {
            newsPublic.add(news);
            return news;
        } else throw new NewsException("Such news already exist");
    }
}
