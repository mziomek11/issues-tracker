package com.mateuszziomek.issuestracker.apigateway.filters;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserRole;
import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import com.mateuszziomek.issuestracker.shared.readmodel.ObjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Component
public class JWTAuthenticationFilter implements GatewayFilter, Ordered {
    private final DiscoveryClient discoveryClient;
    private @Value("${service.users-query.name}") String serviceUsersQueryName;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return onError(exchange, HttpStatus.UNAUTHORIZED);
        }

        var authorizationHeader = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
        if (!authorizationHeader.startsWith("Bearer ")) {
            return onError(exchange, HttpStatus.UNAUTHORIZED);
        }

        var userServices = discoveryClient.getInstances(serviceUsersQueryName);
        if (userServices == null || userServices.isEmpty()) {
            return onError(exchange, HttpStatus.SERVICE_UNAVAILABLE);
        }

        var serviceIndex = ThreadLocalRandom.current().nextInt(userServices.size()) % userServices.size();
        var service = userServices.get(serviceIndex);

        return WebClient
                .create(service.getUri() + "/api/v1/user-management")
                .get()
                .uri("/users/id")
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .header(SecurityHeaders.ISSUES_TRACKER_USER_ROLE, UserRole.SYSTEM.toString())
                .exchangeToMono(response -> {
                    if (response.statusCode().isError()) {
                        return onError(exchange, response.statusCode());
                    }

                    return response.bodyToMono(ObjectId.class)
                            .flatMap(userId -> {
                                request
                                        .mutate()
                                        .header(SecurityHeaders.ISSUES_TRACKER_USER_ID, userId.getId().toString())
                                        .header(SecurityHeaders.ISSUES_TRACKER_USER_ROLE, UserRole.USER.toString())
                                        .build();

                                return chain.filter(exchange);
                            });
                });
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        var response = exchange.getResponse();
        response.setStatusCode(status);

        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
