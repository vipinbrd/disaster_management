package com.disaster.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisasterResponse {
    private Long id;
    private String title;
    private String locationName;
    private String description;
    private List<String> tags;
    private String ownerId;
    private LocalDateTime createdAt;
    private double lat;
    private double lng;
    private String auditTrail;
}
