package com.mateuszziomek.issuestracker.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class ApiGatewayRoutingConfiguration {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/api/v1/issue-management/**")
                        .and()
                        .method(HttpMethod.GET)
                        .negate()
                        .uri("lb://" + System.getenv("SERVICE_ISSUES_COMMAND_NAME").toUpperCase())
                )
                .route(r -> r.path("/api/v1/issue-management/**")
                        .and()
                        .method(HttpMethod.GET)
                        .uri("lb://" + System.getenv("SERVICE_ISSUES_QUERY_NAME").toUpperCase())
                )
                .build();
    }
}
