package com.disaster.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.disaster.dto.DisasterResponse;
import com.disaster.entities.Disaster;

@Component
public class DisasterMapper {
    public DisasterResponse toDto(Disaster d) {
        return new DisasterResponse(
            d.getId(),
            d.getTitle(),
            d.getLocationName(),
            d.getDescription(),
            d.getTags(),
            d.getOwnerId(),
            d.getCreatedAt(),
            d.getLocation().getY(),
            d.getLocation().getX(),
            d.getAuditTrail()
        );
    }
    public List<DisasterResponse> toDtoList(List<Disaster> disasters) {
        return disasters.stream().map(this::toDto).toList();
    }
}
