package com.disaster.dto;

import java.util.List;

import lombok.Data;

@Data
public class DisasterRequest {
    private String title;
    private String locationName;
    private String description;
    private List<String> tags;
    private String ownerId;
//    private double lat;
//    private double lng;
}

