package com.lab.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EntityScan(basePackages = "com.lab.server.entities")
@EnableJpaRepositories
@Slf4j
public class HumanResourcesManagementSystemMvpApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumanResourcesManagementSystemMvpApplication.class, args);
		log.info("Swagger: http://localhost:8080/swagger-ui/index.html");
	}

}
