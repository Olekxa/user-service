package com.greedobank.reports.news;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class NewsPublic {
    private final List<NewsTemplateResponseDTO> newsStorage = new ArrayList<>();

    @ResponseBody
    public NewsTemplateResponseDTO postNews(NewsTemplateRequestDTO request) {
        if (request.getContent().description() == null || request.getContent().title() == null) {
            throw new RuntimeException("description & title can't be empty");
        }
        if (newsStorage.stream().noneMatch(x -> x.content().equals(request.getContent()))) {
            NewsTemplateResponseDTO newsResponse = mapRequestToResponse(request);
            return newsResponse;
        } else throw new RuntimeException("Such news already exist");
    }

    private NewsTemplateResponseDTO mapRequestToResponse(NewsTemplateRequestDTO request) {
        OffsetDateTime timeCreate = OffsetDateTime.now();
        NewsTemplateResponseDTO newsResponse = new NewsTemplateResponseDTO(
                newsStorage.size(),
                request.isDisplayOnSite(),
                request.isSendByEmail(),
                request.getContent(),
                request.getPublicationDate(),
                request.isActive(),
                timeCreate,
                timeCreate);
        newsStorage.add(newsResponse);
        return newsResponse;
    }
}
