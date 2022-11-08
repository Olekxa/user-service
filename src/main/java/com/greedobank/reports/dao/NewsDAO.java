package com.greedobank.reports.dao;

import com.greedobank.reports.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface NewsDAO extends JpaRepository<News, Long> {

    @Query(value = "select * from  news where publication_date > ?1", nativeQuery = true)
    List<News> findAllExpectedNews(OffsetDateTime date);
}
