package com.lab.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.lab.server.entities")
@EnableJpaRepositories
public class HumanResourcesManagementSystemMvpApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumanResourcesManagementSystemMvpApplication.class, args);
	}

}
