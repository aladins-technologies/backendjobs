package com.example.BackendJobs.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendJobs.exception.InvalidIdException;
import com.example.BackendJobs.exception.InvalidPageException;
import com.example.BackendJobs.exception.InvalidStatusException;
import com.example.BackendJobs.exception.JobsException;
import com.example.BackendJobs.exception.JobsNotFoundException;
import com.example.BackendJobs.model.Jobs;
import com.example.BackendJobs.service.JobsService;

@RestController
@RequestMapping("/jobs")
public class JobsController {
	@Autowired
	private JobsService jobsService;
	
	@PostMapping("/createJob")
	public ResponseEntity<?> createJob(@RequestBody Jobs job) {
		Jobs newJob = null;
		try {
			newJob = jobsService.createNewJob(job);
		} catch(JobsException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>("Unable to create new Job", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(newJob, HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestParam int pageNumber) {
		Page<Jobs> jobPage = null;
		try {
			jobPage = jobsService.listAll(pageNumber);
			if(jobPage != null && jobPage.getNumberOfElements() <= 0) {					//elements in selected page
				if(jobPage.getTotalElements() > 0) {
					throw new InvalidPageException("Requested page not available");
				}
				throw new JobsNotFoundException("No available Jobs");
			}
			
		} catch(JobsException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>("Unable to find available Jobs", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(jobPage, HttpStatus.OK);
    }
	
	@GetMapping("/getById")
    public ResponseEntity<?> getById(@RequestParam long id) {
		Jobs job = null;
		try {
			if(id < 1000){
				throw new InvalidIdException("Please provide a valid ID");
			}
			job = jobsService.getById(id);
		} catch(JobsException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>("Unable to find available Jobs", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(job, HttpStatus.FOUND);
    }
	
	@GetMapping("/getByStatus")
    public ResponseEntity<?> getByStatus(@RequestParam int status, @RequestParam int pageNumber) {
		Page<Jobs> jobPage = null;
		try {
			if(status > 3 || status < 0){
				throw new InvalidStatusException("Please provide a valid Status");
			}
			jobPage = jobsService.listByStatus(status, pageNumber);
			if(jobPage != null && jobPage.getNumberOfElements() <= 0) {				//elements in selected page
				if(jobPage.getTotalElements() > 0) {
					throw new InvalidPageException("Requested page not available");
				}
				throw new JobsNotFoundException("No available Jobs");
			}
		} catch(JobsException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>("Unable to find available Jobs", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(jobPage, HttpStatus.FOUND);
    }
	
	@GetMapping("/getByIsActive")
    public ResponseEntity<?> getByIsActive(@RequestParam boolean isActive, @RequestParam int pageNumber) {
		Page<Jobs> jobPage = null;
		try {
			jobPage = jobsService.listByIsActive(isActive, pageNumber);
			if(jobPage != null && jobPage.getNumberOfElements() <= 0) {				//elements in selected page
				if(jobPage.getTotalElements() > 0) {
					throw new InvalidPageException("Requested page not available");
				}
				throw new JobsNotFoundException("No available Jobs");
			}
		} catch(JobsException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>("Unable to find available Jobs", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(jobPage, HttpStatus.FOUND);
    }
	
	@GetMapping("/getByStartDate")
    public ResponseEntity<?> getByStartDate(@RequestParam Timestamp startDate, @RequestParam int pageNumber) {
		Page<Jobs> jobPage = null;
		try {
			jobPage = jobsService.listByStartDate(startDate, pageNumber);
			if(jobPage != null && jobPage.getNumberOfElements() <= 0) {				//elements in selected page
				if(jobPage.getTotalElements() > 0) {
					throw new InvalidPageException("Requested page not available");
				}
				throw new JobsNotFoundException("No available Jobs");
			}
		} catch(JobsException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>("Unable to find available Jobs", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(jobPage, HttpStatus.FOUND);
    }
	
	@GetMapping("/getByEndDate")
    public ResponseEntity<?> getByEndDate(@RequestParam Timestamp endDate, @RequestParam int pageNumber) {
		Page<Jobs> jobPage = null;
		try {
			jobPage = jobsService.listByEndDate(endDate, pageNumber);
			if(jobPage != null && jobPage.getNumberOfElements() <= 0) {				//elements in selected page
				if(jobPage.getTotalElements() > 0) {
					throw new InvalidPageException("Requested page not available");
				}
				throw new JobsNotFoundException("No available Jobs");
			}
		} catch(JobsException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>("Unable to find available Jobs", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(jobPage, HttpStatus.FOUND);
    }
	
	@PutMapping("/updateJobStatus")
	public ResponseEntity<?> updateJobStatus(@RequestParam long id, @RequestParam int status) {
		Jobs newJob = null;
		try {
			if(status > 3 || status < 0){
				throw new InvalidStatusException("Please provide a valid Status");
			}
			newJob = jobsService.updateStatus(id, status);
		} catch(JobsException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>("Unable update Job status", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(newJob, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteById")
	public ResponseEntity<?> deleteById(@RequestParam long id) {
		String msg = "";
		try {
			msg = jobsService.deleteById(id);
		} catch(JobsException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>("Unable delete Job", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
