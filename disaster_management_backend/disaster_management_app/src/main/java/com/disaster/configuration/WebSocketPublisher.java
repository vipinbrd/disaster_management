package com.disaster.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.disaster.dto.DisasterResponse;
import com.disaster.entities.Disaster;
import com.disaster.entities.Resource;
import com.disaster.services.DisasterMapper;
import com.disaster.services.DisasterService;
import com.disaster.services.ResourceMapper;

@Component
public class WebSocketPublisher {
    @Autowired private SimpMessagingTemplate template;
    @Autowired
    private DisasterMapper disasterService;
    @Autowired ResourceMapper resourceMapper;

    public void publishDisasterUpdate(Disaster d) {
    	 
    	    DisasterResponse dto = disasterService.toDto(d);
    	    template.convertAndSend("/topic/disaster_updated", dto);
    }

    public void publishResourceUpdate(Resource r) {
        template.convertAndSend("/topic/resources_updated", resourceMapper.toDto(r));
    }
}