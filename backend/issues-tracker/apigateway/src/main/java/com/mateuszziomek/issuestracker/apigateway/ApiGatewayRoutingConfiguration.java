package com.mateuszziomek.issuestracker.apigateway;

import com.mateuszziomek.issuestracker.apigateway.filters.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
@RequiredArgsConstructor
public class ApiGatewayRoutingConfiguration {
    private static final String ISSUES_BASE_URL = "/api/v1/issue-management";
    private static final String ORGANIZATIONS_BASE_URL = "/api/v1/organization-management";
    private static final String USERS_BASE_URL = "/api/v1/user-management";

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder
                .routes()
                // ISSUES
                .route(r -> r.path(ISSUES_BASE_URL + "/**")
                        .and()
                        .not(p -> p.method(HttpMethod.GET))
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(lb("SERVICE_ISSUES_COMMAND_NAME"))
                )
                .route(r -> r.path(ISSUES_BASE_URL + "/**")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(lb("SERVICE_ISSUES_QUERY_NAME"))
                )
                // ORGANIZATIONS
                .route(r -> r.path(ORGANIZATIONS_BASE_URL + "/**")
                        .and()
                        .not(p -> p.method(HttpMethod.GET))
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(lb("SERVICE_ORGANIZATIONS_COMMAND_NAME"))
                )
                .route(r -> r.path( ORGANIZATIONS_BASE_URL + "/**")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(lb("SERVICE_ORGANIZATIONS_QUERY_NAME"))
                )
                // USERS
                .route(r -> r.path(USERS_BASE_URL + "/users/authentication")
                        .uri(lb("SERVICE_USERS_QUERY_NAME"))
                )
                .route(r -> r.path(USERS_BASE_URL + "/users")
                        .and()
                        .method(HttpMethod.POST)
                        .uri(lb("SERVICE_USERS_COMMAND_NAME"))
                )
                .route(r -> r.path(USERS_BASE_URL + "/**")
                        .and()
                        .not(p -> p.method(HttpMethod.GET))
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(lb("SERVICE_USERS_COMMAND_NAME"))
                )
                .route(r -> r.path(USERS_BASE_URL + "/**")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(lb("SERVICE_USERS_QUERY_NAME"))
                )
                .build();
    }

    private String lb(String service) {
        return "lb://" + System.getenv(service);
    }
}
