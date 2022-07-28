package com.greedobank.reports.service;

import com.greedobank.reports.dto.NewsRequestDTO;
import com.greedobank.reports.dto.NewsResponseDTO;

public interface INewsService {
    NewsResponseDTO create(NewsRequestDTO request);
}
