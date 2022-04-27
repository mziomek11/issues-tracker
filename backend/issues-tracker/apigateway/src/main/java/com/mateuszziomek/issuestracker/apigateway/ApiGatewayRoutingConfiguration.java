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
                        .not(p -> p.method(HttpMethod.GET))
                        .uri("lb://" + System.getenv("SERVICE_ISSUES_COMMAND_NAME"))
                )
                .route(r -> r.path("/api/v1/issue-management/**")
                        .and()
                        .method(HttpMethod.GET)
                        .uri("lb://" + System.getenv("SERVICE_ISSUES_QUERY_NAME"))
                )
                .route(r -> r.path("/api/v1/organization-management/**")
                        .and()
                        .not(p -> p.method(HttpMethod.GET))
                        .uri("lb://" + System.getenv("SERVICE_ORGANIZATIONS_COMMAND_NAME"))
                )
                .route(r -> r.path("/api/v1/organization-management/**")
                        .and()
                        .method(HttpMethod.GET)
                        .uri("lb://" + System.getenv("SERVICE_ORGANIZATIONS_QUERY_NAME"))
                )
                .route(r -> r.path("/api/v1/user-management/users/authentication")
                        .uri("lb://" + System.getenv("SERVICE_USERS_QUERY_NAME"))
                )
                .route(r -> r.path("/api/v1/user-management/**")
                        .and()
                        .not(p -> p.method(HttpMethod.GET))
                        .uri("lb://" + System.getenv("SERVICE_USERS_COMMAND_NAME"))
                )
                .route(r -> r.path("/api/v1/user-management/**")
                        .and()
                        .method(HttpMethod.GET)
                        .uri("lb://" + System.getenv("SERVICE_USERS_QUERY_NAME"))
                )
                .build();
    }
}
