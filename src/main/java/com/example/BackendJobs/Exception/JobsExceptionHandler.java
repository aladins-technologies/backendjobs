package com.example.BackendJobs.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JobsExceptionHandler {
	
	@ExceptionHandler(JobsNotFoundException.class)
	public ResponseEntity<?> jobsNotFoundExceptionHandler(JobsNotFoundException e){
		return new ResponseEntity<>(e.getErrorMsg(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidIdException.class)
	public ResponseEntity<?> invalidJobIdExceptionHandler(InvalidIdException e){
		return new ResponseEntity<>(e.getErrorMsg(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidStatusException.class)
	public ResponseEntity<?> invalidStatusExceptionHandler(InvalidStatusException e){
		return new ResponseEntity<>(e.getErrorMsg(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDateException.class)
	public ResponseEntity<?> invalidDateExceptionHandler(InvalidDateException e){
		return new ResponseEntity<>(e.getErrorMsg(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(JobsException.class)
	public ResponseEntity<?> jobsExceptionHandler(JobsException e){
		return new ResponseEntity<>(e.getErrorMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InvalidPageException.class)
	public ResponseEntity<?> invalidPageExceptionHandler(InvalidPageException e){
		return new ResponseEntity<>(e.getErrorMsg(), HttpStatus.BAD_REQUEST);
	}
}
