package com.mateuszziomek.issuestracker.shared.infrastructure.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public abstract class AbstractRestClient {
    protected final DiscoveryClient discoveryClient;
    protected final String serviceName;
    private final String servicePath;

    protected abstract IllegalStateException createServiceUnavailableException();

    /**
     * @throws IllegalStateException if service is unavailable
     */
    protected WebClient createClient() {
        var services = discoveryClient.getInstances(serviceName);

        if (services == null || services.isEmpty()) {
            throw createServiceUnavailableException();
        }

        var serviceIndex = ThreadLocalRandom.current().nextInt(services.size()) % services.size();
        var service = services.get(serviceIndex);
        var url = service.getUri() + servicePath;

        return WebClient.create(url);
    }
}
