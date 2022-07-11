package com.greedobank.reports.mapper;

import com.greedobank.reports.dto.ContentRequestDTO;
import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.news.ContentDTO;
import com.greedobank.reports.news.NewsDTO;

import javax.swing.text.AbstractDocument;
import java.time.OffsetDateTime;

public class Mapper {

    public static NewsDTO mapToResponse(NewsRequestDTO requestDTO, long id) {
        OffsetDateTime timeCreate = OffsetDateTime.now();
        return new NewsDTO(id,
                requestDTO.displayOnSite(),
                requestDTO.displayOnSite(),
                mapToResponseContent(requestDTO.content()),
                requestDTO.publicationDate(),
                requestDTO.active(),
                timeCreate,
                timeCreate);
    }

    private static ContentDTO mapToResponseContent(ContentRequestDTO requestDTO) {
        return new ContentDTO(requestDTO.title(), requestDTO.description());


    }
}
