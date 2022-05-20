package com.mateuszziomek.issuestracker.issues.query.infrastructure.restclient;

import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification.ReactiveNotificationRestClient;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification.ReactiveNotificationRestClientV1;
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

    @Bean
    public ReactiveNotificationRestClient notificationRestClient(
            DiscoveryClient discoveryClient,
            @Value("${rest.client.notifications.name}") String serviceName
    ) {
        return new ReactiveNotificationRestClientV1(discoveryClient, serviceName);
    }
}
