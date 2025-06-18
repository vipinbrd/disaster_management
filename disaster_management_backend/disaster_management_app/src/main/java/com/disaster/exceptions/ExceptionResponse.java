package com.disaster.exceptions;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExceptionResponse {
	private String message;
	private String description;
	private LocalDateTime localDateTime;

}
