package com.example.BackendJobs.exception;

public class InvalidDateException extends JobsException {

	private static final long serialVersionUID = 1L;

	public InvalidDateException(String msg) {
		super();
		this.setErrorMsg(msg);
	}
}
