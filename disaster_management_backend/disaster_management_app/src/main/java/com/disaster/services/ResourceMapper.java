package com.disaster.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.disaster.dto.ResourceResponse;
import com.disaster.entities.Resource;
@Component
public class ResourceMapper {
    public ResourceResponse toDto(Resource r) {
        return new ResourceResponse(
            r.getId(),
            r.getDisasterId(),
            r.getName(),
            r.getLocationName(),
            r.getType(),
            r.getLocation().getY(), 
            r.getLocation().getX(), 
            r.getCreatedAt()
        );
    }

    public List<ResourceResponse> toDtoList(List<Resource> resources) {
        return resources.stream().map(this::toDto).toList();
    }
}
