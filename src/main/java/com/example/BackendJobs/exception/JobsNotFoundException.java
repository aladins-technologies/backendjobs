package com.example.BackendJobs.exception;

public class JobsNotFoundException extends JobsException {

	private static final long serialVersionUID = 1L;
	
	public JobsNotFoundException(String msg) {
		super();
		this.setErrorMsg(msg);
	}
}
