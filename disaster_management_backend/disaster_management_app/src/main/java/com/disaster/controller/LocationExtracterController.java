package com.disaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.disaster.services.LocationExtractorService;



@RestController
public class LocationExtracterController {
	@Autowired
	private LocationExtractorService ls;
	@GetMapping("/ai")
	public double[] get() throws Exception {
		
//		return ls.LocationExtracter("hi this is vipin");
		return ls.getCoordinatesFromDescription("01 betul madhyapradesh is the best place to visit");
	}

}
