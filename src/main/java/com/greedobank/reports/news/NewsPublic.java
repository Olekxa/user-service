package com.greedobank.reports.news;

import com.greedobank.reports.exeptions.NewsException;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class NewsPublic {
    private List<News> newsPublic = new ArrayList<>();

    @ResponseBody
    public News postNews(News news) {
        if (news.content().description() == null || news.content().title() == null) {
            throw new NewsException("description & title can't be empty");
        }
        if (newsPublic.stream().noneMatch(x -> x.content().equals(news.content()))) {
            News newsToPost = new News(newsPublic.size(),
                    news.displayOnSite(),
                    news.sendByEmail(),
                    new Content(news.content().title(), news.content().description()),
                    news.active(),
                    news.publicationDate(),
                    news.createdAt().withNano(0),
                    news.createdAt().withNano(0));
            newsPublic.add(newsToPost);
            return newsToPost;
        } else throw new NewsException("Such news already exist");
    }
}
