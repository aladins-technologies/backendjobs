package com.example.BackendJobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class BackendJobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendJobsApplication.class, args);
	}

}
