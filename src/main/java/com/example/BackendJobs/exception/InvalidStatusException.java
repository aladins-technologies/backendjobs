package com.example.BackendJobs.exception;

public class InvalidStatusException extends JobsException {

	private static final long serialVersionUID = 1L;
	
	public InvalidStatusException(String msg) {
		super();
		this.setErrorMsg(msg);
	}
}
