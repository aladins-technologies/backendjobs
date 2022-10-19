package com.example.BackendJobs.Exception;

public class InvalidPageException extends JobsException {

	private static final long serialVersionUID = 1L;

	public InvalidPageException(String msg) {
		super();
		this.setErrorMsg(msg);
	}
}
