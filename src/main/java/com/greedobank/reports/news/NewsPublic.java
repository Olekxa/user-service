package com.greedobank.reports.news;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static com.greedobank.reports.mapper.Mapper.mapToResponse;

@Getter
public class NewsPublic {
    private final List<News> newsStorage = new ArrayList<>();

    @ResponseBody
    public News postNews(NewsTemplateRequestDTO request) {
        if (request.getContent().description() == null || request.getContent().title() == null) {
            throw new RuntimeException("description & title can't be empty");
        }
        if (newsStorage.stream().noneMatch(x -> x.content().equals(request.getContent()))) {
            News newsResponse = mapToResponse(request, newsStorage.size());
            newsStorage.add(newsResponse);
            return newsResponse;
        } else throw new RuntimeException("Such news already exist");
    }
}
