package com.example.BackendJobs.Repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.BackendJobs.Model.Jobs;
import com.example.BackendJobs.Util.Status;

@Repository
public interface JobsRepository extends JpaRepository<Jobs, Long>{
	Page<Jobs> findAll(Pageable pageable);
	Page<Jobs> findByStatus(Status status, Pageable pageable);
	Page<Jobs> findByIsActive(boolean isActive, Pageable pageable);
	Page<Jobs> findByStartDate(Timestamp startDate, Pageable pageable);
	Page<Jobs> findByEndDate(Timestamp endDate, Pageable pageable);
	
	@Query(value = "SELECT * FROM jobs WHERE job_id in ?1", nativeQuery = true)
	Page<Jobs> getJobsByIds(List<Integer> ids, Pageable pageable);
	
	@Modifying
    @Transactional
	@Query(value = "DELETE FROM jobs WHERE job_id in ?1", nativeQuery = true)
	int deleteJobsByIds(List<Integer> ids);
	
	@Query(value = "SELECT job_id FROM jobs WHERE job_id in ?1", nativeQuery = true)
	List<Integer> getJobsIdsPresent(List<Integer> ids);
	
	@Query(value = "SELECT * FROM jobs WHERE "
			+ "CASE WHEN :id >= 0 THEN job_id = :id ELSE 1 = 1 END "
			+ "AND CASE WHEN :status >= 0 THEN status = :status ELSE 1 = 1 END "
			+ "AND CASE WHEN :isActive >= 0 THEN is_active = :isActive ELSE 1 = 1 END "
			+ "AND CASE WHEN :startDate IS NOT NULL THEN CAST(start_date AS DATE) = :startDate ELSE 1 = 1 END "
			+ "AND CASE WHEN :endDate IS NOT NULL THEN CAST(end_date AS DATE) = :endDate ELSE 1 = 1 END "
			+ "ORDER BY updated_date desc", nativeQuery = true)
	Page<Jobs> getJobs(@Param("id") int id, 
			@Param("status") int status, 
			@Param("isActive") int isActive, 
			@Param("startDate") String startDate, 
			@Param("endDate") String endDate, 
			Pageable pageable);
}
