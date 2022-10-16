package com.example.BackendJobs.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.BackendJobs.exception.InvalidDateException;
import com.example.BackendJobs.exception.InvalidIdException;
import com.example.BackendJobs.exception.InvalidStatusException;
import com.example.BackendJobs.exception.JobsException;
import com.example.BackendJobs.exception.JobsNotFoundException;
import com.example.BackendJobs.model.Jobs;
import com.example.BackendJobs.repository.JobsRepository;
import com.example.BackendJobs.util.StaticMethods;
import com.example.BackendJobs.util.Status;

@Service
public class JobsService {
	@Autowired
	private JobsRepository jobsRepo;
	
	public Page<Jobs> listAll(int pageNumber){
		pageNumber = pageNumber >= 0 ? pageNumber : 0;										//fallback to first page
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("startDate").descending());
		return jobsRepo.findAll(page);
	}
	
	public Jobs getById(long id) {
		Jobs job = jobsRepo.findById(id).orElse(null);
		if(job == null || job.getJobId() <= 0) {
			throw new JobsNotFoundException("Requested Job not found in server");
		}
        return job;
    }
	
	public Page<Jobs> listByStatus(int status, int pageNumber){
		pageNumber = pageNumber >= 0 ? pageNumber : 0;										//fallback to first page
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("startDate").descending());
		List<Jobs> jobs = jobsRepo.findByStatus(Status.values()[status], page);
		Page<Jobs> pages = new PageImpl<Jobs>(jobs);
		return pages;
	}
	
	public Page<Jobs> listByIsActive(boolean isActive, int pageNumber){
		pageNumber = pageNumber >= 0 ? pageNumber : 0;										//fallback to first page
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("startDate").descending());
		List<Jobs> jobs = jobsRepo.findByIsActive(isActive, page);
		Page<Jobs> pages = new PageImpl<Jobs>(jobs);
		return pages;
	}
	
	public Page<Jobs> listByStartDate(Timestamp startDate, int pageNumber){
		pageNumber = pageNumber >= 0 ? pageNumber : 0;										//fallback to first page
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("updatedDate").descending());
		List<Jobs> jobs = jobsRepo.findByStartDate(startDate, page);
		Page<Jobs> pages = new PageImpl<Jobs>(jobs);
		return pages;
	}
	
	public Page<Jobs> listByEndDate(Timestamp endDate, int pageNumber){
		pageNumber = pageNumber >= 0 ? pageNumber : 0;										//fallback to first page
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("updatedDate").descending());
		List<Jobs> jobs = jobsRepo.findByEndDate(endDate, page);
		Page<Jobs> pages = new PageImpl<Jobs>(jobs);
		return pages;
	}
	
	public Jobs updateStatus(long id, int status) {
		Jobs job = jobsRepo.findById(id).orElse(null);
		if(job == null) {
			throw new InvalidIdException("Requested Job not found in server");
		}
		Status newStatus = Status.values()[status];
		if(job.getStatus() == newStatus) {
			throw new JobsException("Already in " + newStatus.toString() + " state");
		}
		job.setStatus(newStatus);
		Jobs newJob = jobsRepo.save(job);
		return newJob;
	}
	
	public String deleteById(long id) {
		Jobs job = jobsRepo.findById(id).orElse(null);
		if(job == null) {
			throw new InvalidIdException("Requested Job not found in server");
		}
    	this.delete(id);
    	return "Job " + job.getJobName() + " deleted successfully!";
    }
	
	public Jobs createNewJob(Jobs job) {
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if(job.getJobName() == null || job.getJobName().length() < 5) {
			throw new JobsException("Job name must have 5 chars");
		} else if(job.getDescription() == null || job.getDescription().length() <= 0){
			String description = "New job created on " + currentTime;
			job.setDescription(description);
		} else if(job.getStatus() == null || !StaticMethods.isValidEnum(Status.class, job.getStatus().toString())) {
			throw new InvalidStatusException("Please provide a valid Status");
		} else if(job.getStartDate() == null) {
			throw new InvalidDateException("Please select Start date");
		} else if(job.getEndDate() == null) {
			throw new InvalidDateException("Please select End date");
		} else if(job.getUpdatedDate() == null) {
			job.setUpdatedDate(currentTime);
		}
		Jobs newJob = jobsRepo.save(job);
		if(newJob.getJobId() <= 0) {
			throw new JobsException("Unable to create new Job " + job.getJobName());
		}
		return newJob;
	}
	
	public void save(Jobs job) {
		jobsRepo.save(job);
	}
    
    public void delete(long id) {
    	jobsRepo.deleteById(id);
    }
	
}
