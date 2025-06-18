package com.disaster.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceResponse {
    private Long id;
    private Long disasterId;
    private String name;
    private String locationName;
    private String type;
    private double lat;
    private double lng;
    private LocalDateTime createdAt;
}