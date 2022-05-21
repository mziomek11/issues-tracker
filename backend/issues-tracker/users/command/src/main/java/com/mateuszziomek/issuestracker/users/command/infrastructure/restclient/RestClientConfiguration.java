package com.mateuszziomek.issuestracker.users.command.infrastructure.restclient;

import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user.ReactiveUserRestClient;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.user.ReactiveUserRestClientV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestClientConfiguration {
    @Bean
    public ReactiveUserRestClient userRestClient(
            DiscoveryClient discoveryClient,
            @Value("${rest.client.users-query.name}") String serviceName
    ) {
        return new ReactiveUserRestClientV1(discoveryClient, serviceName);
    }
}
