package com.example.BackendJobs.Controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendJobs.Dto.JobUpdateDto;
import com.example.BackendJobs.Exception.InvalidPageException;
import com.example.BackendJobs.Exception.InvalidStatusException;
import com.example.BackendJobs.Exception.JobsException;
import com.example.BackendJobs.Exception.JobsNotFoundException;
import com.example.BackendJobs.Model.Jobs;
import com.example.BackendJobs.Model.User;
import com.example.BackendJobs.Repository.UserRepository;
import com.example.BackendJobs.Service.JobsService;
import com.example.BackendJobs.Util.Status;

@RestController
@RequestMapping("/jobs")
public class JobsController {
	@Autowired
	private JobsService jobsService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
    private UserRepository userRepo;
	
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
	
	@GetMapping("/getJobs")
    public ResponseEntity<?> getJobs(@RequestParam int pageNumber,
    		@RequestParam(required = false) Integer id,
    		@RequestParam(required = false) Status status,
    		@RequestParam(required = false) Boolean isActive,
    		@RequestParam(required = false) Timestamp startDate,
    		@RequestParam(required = false) Timestamp endDate) {
		Page<Jobs> jobPage = null;
		try {
			jobPage = jobsService.getJobs(pageNumber, id, status, isActive, startDate, endDate);
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
	
	@Deprecated
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
	
	@PutMapping("/updateJob")
	public ResponseEntity<?> updateJob(@RequestBody JobUpdateDto updateDto) {
		Jobs newJob = null;
		try {
			newJob = jobsService.updateJob(updateDto);
		} catch(JobsException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>("Unable to update Job", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(newJob, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteByIds")
	public ResponseEntity<?> deleteByIds(@RequestBody List<Integer> ids) {
		String msg = "";
		try {
			msg = jobsService.deleteJobsByIds(ids);
		} catch(JobsException e) {
			throw e;
		} catch(Exception e) {
			return new ResponseEntity<>("Unable to delete Job", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
	
    @PostMapping("/createOrUpdateUser")
    public ResponseEntity<?> createOrUpdateUser(@RequestBody User user) {
        User savedUser = null;
        try {
            String hashPwd = passwordEncoder.encode(user.getPwd());
            if(user.getId() > 0) {						//update password only
            	user = userRepo.findById(user.getId()).get();
        	}
            user.setPwd(hashPwd);
            savedUser = userRepo.save(user);
            if (savedUser.getId() > 0) {
            	return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
            }
        } catch (Exception ex) {
        	return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    
    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam int id) {
        User user = null;
        try {
        	user = userRepo.findById(id).get();
            if (user.getId() > 0) {
            	return new ResponseEntity<>(user, HttpStatus.CREATED);
            }
        } catch (Exception ex) {
        	return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
