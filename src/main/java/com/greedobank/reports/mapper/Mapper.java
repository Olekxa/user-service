package com.greedobank.reports.mapper;

import com.greedobank.reports.news.News;
import com.greedobank.reports.news.NewsTemplateRequestDTO;

import java.time.OffsetDateTime;

public class Mapper {

    public static News mapToResponse(NewsTemplateRequestDTO requestDTO, int id) {
        String timeCreate = String.valueOf(OffsetDateTime.now());
        return new News(
                id,
                requestDTO.isDisplayOnSite(),
                requestDTO.isSendByEmail(),
                requestDTO.getContent(),
                requestDTO.getPublicationDate(),
                requestDTO.isActive(),
                timeCreate,
                timeCreate);
    }
}
