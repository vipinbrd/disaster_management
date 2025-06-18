package com.disaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.disaster.dto.DisasterRequest;
import com.disaster.dto.DisasterResponse;
import com.disaster.dto.TweetRequest;
import com.disaster.entities.Disaster;
import com.disaster.services.DisasterMapper;
import com.disaster.services.DisasterService;

@RestController
@RequestMapping("/api/mock-twitter")
public class MockTwitterController {

    @Autowired private DisasterService disasterService;
   @Autowired
   private DisasterMapper disasterMapper;

    private static final List<String> DISASTER_KEYWORDS = List.of("flood", "earthquake", "fire", "cyclone", "storm", "landslide");

    @PostMapping("/tweet")
    public ResponseEntity<?> simulateTweet(@RequestBody TweetRequest tweet) throws Exception {
        for (String keyword : DISASTER_KEYWORDS) {
            if (tweet.getContent().toLowerCase().contains(keyword)) {
                DisasterRequest disasterRequest = new DisasterRequest();
                disasterRequest.setTitle("Auto Detected: " + keyword);
                disasterRequest.setDescription(tweet.getContent());
                disasterRequest.setTags(List.of("auto", keyword));
                disasterRequest.setOwnerId("auto-tweet");
               
                DisasterResponse created = disasterMapper.toDto(disasterService.create(disasterRequest));
                return ResponseEntity.ok(created);
            }
        }
        return ResponseEntity.ok("No disaster keyword detected in tweet.");
    }
}