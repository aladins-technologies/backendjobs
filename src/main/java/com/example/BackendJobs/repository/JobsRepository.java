package com.example.BackendJobs.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BackendJobs.model.Jobs;
import com.example.BackendJobs.util.Status;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, Long>{
	Page<Jobs> findAll(Pageable pageable);
	List<Jobs> findByStatus(Status status, Pageable pageable);
	List<Jobs> findByIsActive(boolean isActive, Pageable pageable);
	List<Jobs> findByStartDate(Timestamp startDate, Pageable pageable);			//TODO compare without time(ie, Date only)
	List<Jobs> findByEndDate(Timestamp endDate, Pageable pageable);				//TODO compare without time(ie, Date only)
	
	
}
