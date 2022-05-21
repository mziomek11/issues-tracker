package com.mateuszziomek.issuestracker.issues.command.infrastructure.restclient;

import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.ReactiveOrganizationRestClient;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.ReactiveOrganizationRestClientV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestClientConfiguration {
    @Bean
    public ReactiveOrganizationRestClient organizationRestClient(
            DiscoveryClient discoveryClient,
            @Value("${rest.client.organizations-query.name}") String serviceName
    ) {
        return new ReactiveOrganizationRestClientV1(discoveryClient, serviceName);
    }
}
