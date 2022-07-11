package com.greedobank.reports.news;

import com.greedobank.reports.dto.NewsRequestDTO;
import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static com.greedobank.reports.mapper.Mapper.mapToResponse;

@Getter
public class NewsPublic {
    List<NewsDTO> newsPublic;
    private long id;

    public NewsPublic() {
        this.newsPublic = new ArrayList<>();
        id = 0;
    }

    @ResponseBody
    public NewsDTO postNews(NewsRequestDTO request) {
        if (request.content().description() == null || request.content().title() == null) {
            throw new RuntimeException("description & title can't be empty");
        }
        NewsDTO newsDTO = mapToResponse(request, id++);
        newsPublic.add(newsDTO);
        return newsDTO;
    }
}

