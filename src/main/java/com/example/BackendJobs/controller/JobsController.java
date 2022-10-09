package com.example.BackendJobs.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendJobs.model.Jobs;
import com.example.BackendJobs.service.JobsService;

@RestController
@RequestMapping("/jobs")
public class JobsController {
	@Autowired
	private JobsService jobsService;
	
	@GetMapping("/getAll")
    public Page<Jobs> getAll(@RequestParam int pageNumber) {
		return jobsService.listAll(pageNumber);
    }
	
	@GetMapping("/getById")
    public Jobs getById(@RequestParam long id) {
		return jobsService.getById(id);
    }
	
	@GetMapping("/getByStatus")
    public List<Jobs> getByStatus(@RequestParam int status, @RequestParam int pageNumber) {
		return jobsService.listByStatus(status, pageNumber);
    }
	
	@GetMapping("/getByIsActive")
    public List<Jobs> getByIsActive(@RequestParam boolean isActive, @RequestParam int pageNumber) {
		return jobsService.listByIsActive(isActive, pageNumber);
    }
	
	@GetMapping("/getByStartDate")
    public List<Jobs> getByStartDate(@RequestParam Timestamp startDate, @RequestParam int pageNumber) {
		return jobsService.listByStartDate(startDate, pageNumber);
    }
	
	@GetMapping("/getByEndDate")
    public List<Jobs> getByEndDate(@RequestParam Timestamp endDate, @RequestParam int pageNumber) {
		return jobsService.listByEndDate(endDate, pageNumber);
    }
	
	@PutMapping("/updateJobStatus")
	public String updateJobStatus(@RequestParam long id, @RequestParam int status) {
		return jobsService.updateStatus(id, status);
	}
	
	@DeleteMapping("/deleteById")
	public String deleteById(@RequestParam long id) {
		return jobsService.deleteById(id);
	}
}
