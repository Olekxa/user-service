package com.greedobank.reports.dao;

import org.springframework.context.annotation.Profile;

import org.springframework.stereotype.Repository;

@Repository
@Profile("production")
public interface ProductionNewsDAO extends NewsDao {
}
