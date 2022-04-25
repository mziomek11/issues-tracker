package com.mateuszziomek.issuestracker.organizations.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrganizationQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizationQueryApplication.class, args);
	}

}
