package com.disaster.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disaster.entities.Report;
import com.disaster.repository.ReportRepository;

@Service
public class ReportService {
    @Autowired private ReportRepository reportRepository;

    public Report submitReport(Report report) {
        report.setCreatedAt(LocalDateTime.now());
        report.setVerificationStatus("PENDING");
        return reportRepository.save(report);
    }

    public List<Report> getReportsForDisaster(Long disasterId) {
        return reportRepository.findByDisasterId(disasterId);
    }

    public Report verifyReport(Long id, String status) {
        Report report = reportRepository.findById(id).orElseThrow();
        report.setVerificationStatus(status);
        return reportRepository.save(report);
    }
}