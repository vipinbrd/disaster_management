package com.disaster.services;

import java.time.LocalDateTime;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disaster.configuration.WebSocketPublisher;
import com.disaster.entities.Resource;
import com.disaster.repository.ResourceRepository;

@Service
public class ResourceService {
    @Autowired private ResourceRepository resourceRepo;
    @Autowired private GeometryFactory geometryFactory;
    @Autowired private WebSocketPublisher webSocketPublisher;
    @Autowired private LocationExtractorService locationExtractorService;

    public Resource create(Resource resource) throws Exception {
    	double[] coordinate=locationExtractorService.getCoordinatesFromDescription(resource.getLocationName());
        resource.setLocation(geometryFactory.createPoint(new Coordinate(coordinate[1], coordinate[0])));
        resource.setCreatedAt(LocalDateTime.now());
        Resource saved = resourceRepo.save(resource);
        webSocketPublisher.publishResourceUpdate(saved);
        return saved;
    }

    public List<Resource> findNearby(double lat, double lng, double radiusMeters) {
        String pointWKT = String.format("POINT(%f %f)", lat, lng);
        System.out.println(pointWKT);
        return resourceRepo.findNearby(pointWKT, radiusMeters);
    }
}