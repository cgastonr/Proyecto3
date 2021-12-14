package com.report.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.report.entity.Report;

@Repository
public interface ReportRepository extends ReactiveMongoRepository<Report,String> {

	
}
