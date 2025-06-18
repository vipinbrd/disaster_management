package com.disaster.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Disaster {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String locationName;
    private String description;

    @ElementCollection
    private List<String> tags;

    private String ownerId;
    private LocalDateTime createdAt;

    @Column(columnDefinition = "POINT")
    private Point location;

    @Column(columnDefinition = "json")
    private String auditTrail;
}