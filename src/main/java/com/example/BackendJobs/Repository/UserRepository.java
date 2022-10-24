package com.example.BackendJobs.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BackendJobs.Model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	List<User> findByName(String name);
}
