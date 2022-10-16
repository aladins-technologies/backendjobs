package com.example.BackendJobs.exception;

public class InvalidIdException extends JobsException {

	private static final long serialVersionUID = 1L;
	
	public InvalidIdException(String msg) {
		super();
		this.setErrorMsg(msg);
	}
}
