package com.trizzo.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.trizzo.ppmtool.domain.Project;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectNotFoundException extends RuntimeException{
	public ProjectNotFoundException(String message) 
	{
		super(message); 
	}
}
