package com.disaster.dto;

import lombok.Data;

@Data
public class TweetRequest {
    private String content;
    private String locationName;
    private double lat;
    private double lng;
}