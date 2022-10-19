package com.example.BackendJobs.Exception;

public class JobsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public JobsException(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}
	
	public JobsException() {
		super();
	}
}
