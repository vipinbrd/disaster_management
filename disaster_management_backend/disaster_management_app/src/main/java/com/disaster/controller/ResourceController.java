package com.disaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.disaster.dto.ResourceResponse;
import com.disaster.entities.Resource;
import com.disaster.services.ResourceMapper;
import com.disaster.services.ResourceService;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    @Autowired private ResourceService resourceService;
    @Autowired private ResourceMapper resourceMapper;

    @PostMapping
    public ResponseEntity<ResourceResponse> create(@RequestBody Resource resource) throws Exception {
        return ResponseEntity.ok(resourceMapper.toDto(resourceService.create(resource)));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<ResourceResponse>> findNearby(@RequestParam("lat") double lat, @RequestParam("lng") double lng, @RequestParam double radius) {
        return ResponseEntity.ok(resourceMapper.toDtoList(resourceService.findNearby(lat, lng, radius)));
    }
}
