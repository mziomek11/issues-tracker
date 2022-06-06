package com.mateuszziomek.issuestracker.apigateway;

import com.mateuszziomek.issuestracker.apigateway.filters.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
@RequiredArgsConstructor
public class ApiGatewayRoutingConfiguration {
    private static final String ISSUES_BASE_URL = "/api/v1/issue-management";
    private static final String NOTIFICATIONS_BASE_URL = "/api/v1/notification-management";
    private static final String ORGANIZATIONS_BASE_URL = "/api/v1/organization-management";
    private static final String USERS_BASE_URL = "/api/v1/user-management";

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private @Value("${service.issues-command.name}") String serviceIssuesCommandName;
    private @Value("${service.issues-query.name}") String serviceIssuesQueryName;
    private @Value("${service.notifications.name}") String serviceNotificationsName;
    private @Value("${service.organizations-command.name}") String serviceOrganizationsCommandName;
    private @Value("${service.organizations-query.name}") String serviceOrganizationsQueryName;
    private @Value("${service.users-command.name}") String serviceUsersCommandName;
    private @Value("${service.users-query.name}") String serviceUsersQueryName;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder
                .routes()
                // ISSUES
                .route(r -> r.path(ISSUES_BASE_URL + "/**")
                        .and()
                        .not(p -> p.method(HttpMethod.GET))
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(lb(serviceIssuesCommandName))
                )
                .route(r -> r.path(ISSUES_BASE_URL + "/**")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(lb(serviceIssuesQueryName))
                )
                // NOTIFICATIONS
                .route(r -> r.path(NOTIFICATIONS_BASE_URL + "/**")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(lb(serviceNotificationsName))
                )
                // ORGANIZATIONS
                .route(r -> r.path(ORGANIZATIONS_BASE_URL + "/**")
                        .and()
                        .not(p -> p.method(HttpMethod.GET))
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(lb(serviceOrganizationsCommandName))
                )
                .route(r -> r.path( ORGANIZATIONS_BASE_URL + "/**")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(lb(serviceOrganizationsQueryName))
                )
                // USERS
                .route(r -> r.path(USERS_BASE_URL + "/users/authentication")
                        .uri(lb(serviceUsersQueryName))
                )
                .route(r -> r.path(USERS_BASE_URL + "/**")
                        .and()
                        .not(p -> p.method(HttpMethod.GET))
                        .uri(lb(serviceUsersCommandName))
                )
                .route(r -> r.path(USERS_BASE_URL + "/**")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri(lb(serviceUsersQueryName))
                )
                .build();
    }

    private String lb(String serviceName) {
        return "lb://" + serviceName;
    }
}
