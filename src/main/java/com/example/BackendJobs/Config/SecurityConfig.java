package com.example.BackendJobs.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.BackendJobs.Filter.AuthoritiesLoggingAfterFilter;
import com.example.BackendJobs.Filter.AuthoritiesLoggingAtFilter;
import com.example.BackendJobs.Filter.RequestValidationBeforeFilter;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().and()
				.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
				.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
				.authorizeHttpRequests()
				.antMatchers("/createJob", "/getJobs", "/updateJob").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
				.antMatchers("/deleteByIds", "/getUser", "/createOrUpdateUser").hasAuthority("ROLE_ADMIN")
				.and().formLogin()
				.and().httpBasic();
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
