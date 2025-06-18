package com.disaster.services;

import java.time.LocalDateTime;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.disaster.configuration.WebSocketPublisher;
import com.disaster.dto.DisasterRequest;
import com.disaster.dto.DisasterResponse;
import com.disaster.entities.Disaster;
import com.disaster.repository.DisasterRepository;

@Service
public class DisasterService {
    @Autowired private DisasterRepository disasterRepo;
    @Autowired private GeometryFactory geometryFactory;
    @Autowired private WebSocketPublisher wsPublisher;
    @Autowired private DisasterMapper disasterMapper;
    @Autowired private LocationExtractorService locationExtractorService;

    public Disaster create(DisasterRequest req) throws Exception {
    	double[] coordinate=locationExtractorService.getCoordinatesFromDescription(req.getDescription());
    	String locationName=locationExtractorService.extractLocationFromText(req.getDescription());
        Point point = geometryFactory.createPoint(new Coordinate(coordinate[0], coordinate[1]));
        point.setSRID(4326);
        System.out.println(point.toText()+"???????????????????????VIPIN");
        Disaster d = new Disaster();
        d.setTitle(req.getTitle());
        d.setLocationName(locationName);
        d.setDescription(req.getDescription());
        d.setTags(req.getTags());
        d.setOwnerId(req.getOwnerId());
        d.setCreatedAt(LocalDateTime.now());
        d.setLocation(point);
        Disaster saved = disasterRepo.save(d);
        System.out.println(saved);
        wsPublisher.publishDisasterUpdate(saved);
        return saved;
    }

    public List<DisasterResponse> findAll() {
        List<Disaster>disasters= disasterRepo.findAll();
        return disasterMapper.toDtoList(disasters);
         
        
    }

    public List<DisasterResponse> findByTag(String tag) {
        return  disasterMapper.toDtoList(disasterRepo.findByTagsContaining(tag));
    }

    public DisasterResponse update(Long id, DisasterRequest req) throws Exception {
    	double[] coordinate=locationExtractorService.getCoordinatesFromDescription(req.getDescription());
    	String locationName=locationExtractorService.extractLocationFromText(req.getDescription());
        Disaster d = disasterRepo.findById(id).orElseThrow();
        d.setTitle(req.getTitle());
        d.setDescription(req.getDescription());
        d.setTags(req.getTags());
        d.setLocationName(locationName);
        d.setLocation(geometryFactory.createPoint(new Coordinate(coordinate[1], coordinate[0])));
        Disaster updated = disasterRepo.save(d);
        wsPublisher.publishDisasterUpdate(updated);
        return disasterMapper.toDto(updated);
    }

    public void delete(Long id) {
        Disaster d = disasterRepo.findById(id).orElseThrow();
        disasterRepo.delete(d);
        
    }
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

}