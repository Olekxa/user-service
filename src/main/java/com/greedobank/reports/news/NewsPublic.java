package com.greedobank.reports.news;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NewsPublic {
    private final List<NewsDTO> newsDTOPublic = new ArrayList<>();

    @ResponseBody
    public NewsDTO postNews(NewsDTO newsDTO) {
        if (newsDTO.content().description() == null || newsDTO.content().title() == null) {
            throw new RuntimeException("description & title can't be empty");
        }
        if (newsDTOPublic.stream().noneMatch(x -> x.content().equals(newsDTO.content()))) {
            NewsDTO newsDTOToPost = new NewsDTO(newsDTOPublic.size(),
                    newsDTO.displayOnSite(),
                    newsDTO.sendByEmail(),
                    new Content(newsDTO.content().title(), newsDTO.content().description()),
                    newsDTO.active(),
                    newsDTO.publicationDate(),
                    newsDTO.createdAt().withNano(0),
                    newsDTO.createdAt().withNano(0));
            newsDTOPublic.add(newsDTOToPost);
            return newsDTOToPost;
        } else throw new RuntimeException("Such news already exist");
    }
}
