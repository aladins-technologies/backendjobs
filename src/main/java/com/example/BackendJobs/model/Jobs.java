package com.example.BackendJobs.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.BackendJobs.util.Status;

@Entity
public class Jobs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long jobId;
	private String jobName;
	private String description;
	private Status status;
	private Timestamp startDate;
	private Timestamp endDate;
	private Timestamp updatedDate;
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
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
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
	
	public Jobs() {
		super();
	}
	
	public Jobs(Long jobId, String jobName, String description, Status status, Timestamp startDate, Timestamp endDate,
			Timestamp updatedDate, int errors, boolean isActive) {
		super();
		this.jobId = jobId;
		this.jobName = jobName;
		this.description = description;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.updatedDate = updatedDate;
		this.errors = errors;
		this.isActive = isActive;
	}
	
	@Override
	public String toString() {
		return "Jobs [jobId=" + jobId + ", jobName=" + jobName + ", description=" + description + ", status=" + status + ", startDate="
				+ startDate + ", endDate=" + endDate + ", updatedDate=" + updatedDate + ", errors=" + errors
				+ ", isActive=" + isActive + "]";
	}
	
	
}
