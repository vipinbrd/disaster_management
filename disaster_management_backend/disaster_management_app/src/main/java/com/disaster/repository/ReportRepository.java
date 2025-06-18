package com.disaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.disaster.entities.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByDisasterId(Long disasterId);
}