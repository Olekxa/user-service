package com.greedobank.reports.news;

import com.greedobank.reports.dto.NewsRequestDTO;
import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.greedobank.reports.mapper.Mapper.mapToResponse;

@Getter
public class NewsPublic {
    private static long id = 0;

    @ResponseBody
    public NewsDTO postNews(NewsRequestDTO request) {
//        if (request.getContentDTO().description() == null || request.getContentDTO().title() == null) {
//            throw new RuntimeException("description & title can't be empty");
//        }
        return mapToResponse(request, id++);
    }
}

