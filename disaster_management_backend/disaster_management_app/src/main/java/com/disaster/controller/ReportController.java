package com.disaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.disaster.entities.Report;
import com.disaster.services.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired private ReportService reportService;

    @PostMapping
    public ResponseEntity<Report> submit(@RequestBody Report report) {
        return ResponseEntity.ok(reportService.submitReport(report));
    }

    @GetMapping("/disaster/{disasterId}")
    public ResponseEntity<List<Report>> getByDisaster(@PathVariable Long disasterId) {
        return ResponseEntity.ok(reportService.getReportsForDisaster(disasterId));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<Report> verify(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(reportService.verifyReport(id, status));
    }
}
