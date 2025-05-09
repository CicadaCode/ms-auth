package com.exam.ms_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.exam.ms_auth.repository")
@EntityScan("com.exam.ms_auth.entity")
public class MsAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsAuthApplication.class, args);
	}
}
