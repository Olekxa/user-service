package com.greedobank.reports.service;

import com.greedobank.reports.dto.request.NewsRequestDTO;
import com.greedobank.reports.dto.response.NewsResponseDTO;

public interface INewsService {
    NewsResponseDTO create(NewsRequestDTO request);

    NewsResponseDTO get(long id);

    void patch(long id, NewsRequestDTO updateDTO);

    public void delete(long id);
}
