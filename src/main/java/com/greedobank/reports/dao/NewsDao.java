package com.greedobank.reports.dao;

import com.greedobank.reports.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsDao extends JpaRepository<News, Integer> {
}
