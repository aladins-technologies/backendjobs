package com.example.BackendJobs.Exception;

public class InvalidDateException extends JobsException {

	private static final long serialVersionUID = 1L;

	public InvalidDateException(String msg) {
		super();
		this.setErrorMsg(msg);
	}
}
