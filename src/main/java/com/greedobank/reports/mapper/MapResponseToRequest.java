package com.greedobank.reports.mapper;

import com.greedobank.reports.dto.request.ContentRequestDTO;
import com.greedobank.reports.dto.request.NewsRequestDTO;
import com.greedobank.reports.dto.response.NewsResponseDTO;

public class MapResponseToRequest {
    public NewsRequestDTO mapResponseToRequest(NewsResponseDTO response) {
        return new NewsRequestDTO(
                response.displayOnSite(),
                response.sendByEmail(),
                new ContentRequestDTO(
                        response.content().title(),
                        response.content().description()),
                response.publicationDate(),
                response.active());
    }
}
