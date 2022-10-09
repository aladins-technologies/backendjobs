package com.example.BackendJobs.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.BackendJobs.model.Jobs;
import com.example.BackendJobs.repository.JobsRepository;

import com.example.BackendJobs.util.Status;

@Service
public class JobsService {
	@Autowired
	private JobsRepository jobsRepo;
	
	public Page<Jobs> listAll(int pageNumber){
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("startDate").descending());
		return jobsRepo.findAll(page);
	}
	
	public Jobs getById(long id) {
		Jobs job = jobsRepo.findById(id).orElse(null);
        return job;
    }
	
	public List<Jobs> listByStatus(int status, int pageNumber){
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("startDate").descending());
		return jobsRepo.findByStatus(Status.values()[status], page);
	}
	
	public List<Jobs> listByIsActive(boolean isActive, int pageNumber){
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("startDate").descending());
		return jobsRepo.findByIsActive(isActive, page);
	}
	
	public List<Jobs> listByStartDate(Timestamp startDate, int pageNumber){
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("updatedDate").descending());
		return jobsRepo.findByStartDate(startDate, page);
	}
	
	public List<Jobs> listByEndDate(Timestamp endDate, int pageNumber){
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("updatedDate").descending());
		return jobsRepo.findByEndDate(endDate, page);
	}
	
	public String updateStatus(long id, int status) {
		Jobs job = jobsRepo.findById(id).orElse(null);
		if(job == null) {
			return "No Job is present with ID " + id;
		}
		Status newStatus = Status.values()[status];
		if(job.getStatus() == newStatus) {
			return "Already in " + newStatus.toString() + " state";
		}
		job.setStatus(newStatus);
		jobsRepo.save(job);
		return "Changed status to " + newStatus.toString();
	}
	
	public String deleteById(long id) {
		Jobs job = jobsRepo.findById(id).orElse(null);
		if(job == null) {
			return "No Job is present with ID " + id;
		}
    	this.delete(id);
    	return "Job " + job.getJobName() + " deleted successfully!";
    }
	
	public void save(Jobs job) {
		jobsRepo.save(job);
	}
    
    public void delete(long id) {
    	jobsRepo.deleteById(id);
    }
	
}
