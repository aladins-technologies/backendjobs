package com.example.BackendJobs.Service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.BackendJobs.Dto.JobUpdateDto;
import com.example.BackendJobs.Exception.InvalidDateException;
import com.example.BackendJobs.Exception.InvalidIdException;
import com.example.BackendJobs.Exception.InvalidStatusException;
import com.example.BackendJobs.Exception.JobsException;
import com.example.BackendJobs.Exception.JobsNotFoundException;
import com.example.BackendJobs.Model.Jobs;
import com.example.BackendJobs.Repository.JobsRepository;
import com.example.BackendJobs.Util.StaticMethods;
import com.example.BackendJobs.Util.Status;

@Service
public class JobsService {
	@Autowired
	private JobsRepository jobsRepo;
	
	public Page<Jobs> listAll(int pageNumber){
		pageNumber = pageNumber >= 0 ? pageNumber : 0;										//fallback to first page
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("updatedDate").descending());
		return jobsRepo.findAll(page);
	}
	
	public Page<Jobs> getJobs(int pageNumber, Integer id, Status status, Boolean isActive, Timestamp startDate, Timestamp endDate){
		pageNumber = pageNumber >= 0 ? pageNumber : 0;										//fallback to first page
		
		if(id == null) {
			id = -1;
		}
		int statusInt = -1;
		if(status != null) {
			statusInt = status.ordinal();
		}
		int isActiveInt = -1;
		if(isActive != null) {
			isActiveInt = StaticMethods.intValue(isActive);
		}
		String startDateString = null;
		if(startDate != null) {
			startDateString = StaticMethods.getDate(startDate);
		}
		String endDateString = null;
		if(endDate != null) {
			endDateString = StaticMethods.getDate(endDate);
		}
		
		Pageable page = PageRequest.of(pageNumber, 10);
		Page<Jobs> pages = jobsRepo.getJobs(id, statusInt, isActiveInt, startDateString, endDateString, page);
		return pages;
	}
	
	public Jobs getById(long id) {
		Jobs job = jobsRepo.findById(id).orElse(null);
		if(job == null || job.getJobId() <= 0) {
			throw new JobsNotFoundException("Requested Job not found in server");
		}
        return job;
    }
	
	public Page<Jobs> listByStatus(Status status, int pageNumber){
		pageNumber = pageNumber >= 0 ? pageNumber : 0;										//fallback to first page
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("updatedDate").descending());
		Page<Jobs> pages = jobsRepo.findByStatus(status, page);
		return pages;
	}
	
	public Page<Jobs> listByIsActive(boolean isActive, int pageNumber){
		pageNumber = pageNumber >= 0 ? pageNumber : 0;										//fallback to first page
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("updatedDate").descending());
		Page<Jobs> pages = jobsRepo.findByIsActive(isActive, page);
		return pages;
	}
	
	public Page<Jobs> listByStartDate(Timestamp startDate, int pageNumber){
		pageNumber = pageNumber >= 0 ? pageNumber : 0;										//fallback to first page
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("updatedDate").descending());
		Page<Jobs> pages = jobsRepo.findByStartDate(startDate, page);
		return pages;
	}
	
	public Page<Jobs> listByEndDate(Timestamp endDate, int pageNumber){
		pageNumber = pageNumber >= 0 ? pageNumber : 0;										//fallback to first page
		Pageable page = PageRequest.of(pageNumber, 10, Sort.by("updatedDate").descending());
		Page<Jobs> pages = jobsRepo.findByEndDate(endDate, page);
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
	
	public Jobs updateJob(JobUpdateDto updateDto) {
		Jobs job = jobsRepo.findById(updateDto.getJobId()).orElse(null);
		if(job == null) {
			throw new InvalidIdException("Requested Job not found in server");
		}
		boolean updated = false;
		if(updateDto.getStatus() != null && !job.getStatus().equals(updateDto.getStatus())) {
			if(updateDto.getStatus().equals(Status.KILL)) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if(authentication == null || !authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
					throw new JobsException("Only Admin can KILL a job");
				}
			}
			
			job.setStatus(updateDto.getStatus());
			updated = true;
		}
		if(updateDto.getJobName() != null && !job.getJobName().equals(updateDto.getJobName())) {
			job.setJobName(updateDto.getJobName());
			updated = true;
		}
		if(updateDto.getDescription() != null && !job.getDescription().equals(updateDto.getDescription())) {
			job.setDescription(updateDto.getDescription());
			updated = true;
		}
		if(updateDto.getEndDate() != null && !job.getEndDate().equals(updateDto.getEndDate())) {
			if(!updateDto.getEndDate().after(job.getStartDate())) {
				throw new InvalidDateException("End date must be after Satrt date");
			}
			job.setEndDate(updateDto.getEndDate());
			updated = true;
		}
		if(job.getErrors() != updateDto.getErrors()) {
			job.setErrors(updateDto.getErrors());
			updated = true;
		}
		if(job.isActive() != updateDto.isActive()) {
			job.setActive(updateDto.isActive());
			updated = true;
		}
		
		if(!updated) {
			throw new JobsException("Nothing to update");
		}
		
		job.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
		return jobsRepo.save(job);
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
		}
		job.setUpdatedDate(currentTime);											//No access from front-end
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
	
    public String deleteJobsByIds(List<Integer> ids){
    	if(ids.size() <= 0) {
    		throw new JobsException("Please provide IDs to delete Jobs");
    	}
    	List<Integer> idsOfJobsPresent = jobsRepo.getJobsIdsPresent(ids);
    	ids.removeAll(idsOfJobsPresent);										//find Jobs not in server
    	int deleted = 0;
    	if(ids.size() > 0) {
    		throw new JobsException("Jobs not present in server, " + ids);
    	} else {
    		deleted = jobsRepo.deleteJobsByIds(idsOfJobsPresent);
    	}
		return deleted > 1 ? (deleted + " Jobs Deleted, ") : "Job Deleted, " + idsOfJobsPresent;
	}
	
}
