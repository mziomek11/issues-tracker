package com.mateuszziomek.issuestracker.issues.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IssueQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueQueryApplication.class, args);
	}

}
