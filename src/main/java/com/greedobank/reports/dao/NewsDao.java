package com.greedobank.reports.dao;

import com.greedobank.reports.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsDao extends JpaRepository<News, Long> {
}
