package com.mateuszziomek.issuestracker.organizations.query.infrastructure.restclient;

import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification.NotificationRestClient;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification.NotificationRestClientV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestClientConfiguration {
    @Bean
    public NotificationRestClient notificationRestClient(
            DiscoveryClient discoveryClient,
            @Value("${rest.client.notifications.name}") String serviceName
    ) {
        return new NotificationRestClientV1(discoveryClient, serviceName);
    }
}
