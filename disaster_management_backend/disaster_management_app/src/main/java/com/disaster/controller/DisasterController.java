package com.disaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.disaster.dto.DisasterRequest;
import com.disaster.dto.DisasterResponse;
import com.disaster.entities.Disaster;
import com.disaster.services.DisasterService;

@RestController
@RequestMapping("/api/disasters")
public class DisasterController {
    @Autowired private DisasterService disasterService;

    @PostMapping
    public ResponseEntity<DisasterResponse> create(@RequestBody DisasterRequest req) throws Exception {
    	Disaster d=disasterService.create(req);
    	
        return ResponseEntity.ok(disasterService.toDto(d));
    }

    @GetMapping
    public ResponseEntity<List<DisasterResponse>> findAll(@RequestParam(required = false) String tag) {
        return ResponseEntity.ok(tag == null ? disasterService.findAll() : disasterService.findByTag(tag));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisasterResponse> update(@PathVariable Long id, @RequestBody DisasterRequest req) throws Exception {
        return ResponseEntity.ok(disasterService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        disasterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
