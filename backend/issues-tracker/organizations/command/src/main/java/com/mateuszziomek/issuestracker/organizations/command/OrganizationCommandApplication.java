package com.mateuszziomek.issuestracker.organizations.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrganizationCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizationCommandApplication.class, args);
	}

}
