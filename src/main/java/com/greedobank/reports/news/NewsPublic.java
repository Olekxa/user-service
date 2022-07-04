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

    public void postNews(News news) {
        if (news.getContent().getContent() == null || news.getContent().getTitle() == null) {
            throw new NewsException("content & null cannot be empty");
        }
        if (newsPublic.stream().noneMatch(x -> x.equals(news) || x.getContent().equals(news.getContent()))) {
            newsPublic.add(news);
        } else throw new NewsException("Such news already exist");
    }

    public String getNews(int id) {
        if (newsPublic.isEmpty() || newsPublic.size() < id) {
            return "News not found";
        } else return String.valueOf(newsPublic.get(id));
    }

    public String getAllNews() {
        if (newsPublic.isEmpty()) {
            return "No news yet";
        } else {
            StringBuilder sb = new StringBuilder();
            for (News news : newsPublic) {
                sb.append(news.toString());
                sb.append("\n");
            }
            return sb.toString();
        }
    }
}
