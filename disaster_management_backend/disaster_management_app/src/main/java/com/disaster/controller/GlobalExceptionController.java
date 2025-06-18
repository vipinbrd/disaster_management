package com.disaster.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.disaster.exceptions.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionController {
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse>ExceptionHandler(Exception e,WebRequest webRequest){
		ExceptionResponse er=new ExceptionResponse();
		er.setDescription(webRequest.getDescription(false));
		er.setMessage(e.getMessage());
		er.setLocalDateTime(LocalDateTime.now());
		return new ResponseEntity<ExceptionResponse>(er,HttpStatus.BAD_REQUEST);
		
		
	}

}
