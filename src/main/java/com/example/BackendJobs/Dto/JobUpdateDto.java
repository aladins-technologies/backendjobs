package com.example.BackendJobs.Dto;

import java.sql.Timestamp;

import com.example.BackendJobs.Util.Status;

public class JobUpdateDto {
	
	private Long jobId;
	private String jobName;
	private String description;
	private Status status;
	private Timestamp endDate;
	private int errors;
	private boolean isActive;
	
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public int getErrors() {
		return errors;
	}
	public void setErrors(int errors) {
		this.errors = errors;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public JobUpdateDto() {

	}
	
	public JobUpdateDto(String jobName, String description, Status status, Timestamp endDate, int errors,
			boolean isActive) {
		this.jobName = jobName;
		this.description = description;
		this.status = status;
		this.endDate = endDate;
		this.errors = errors;
		this.isActive = isActive;
	}
	
}
