package org.example.issuestracker.organizations.query.infrastructure.query;

import org.example.cqrs.query.dispatcher.DefaultQueryDispatcher;
import org.example.cqrs.query.dispatcher.QueryDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationQueryConfiguration {
    @Bean
    public QueryDispatcher queryDispatcher() {
        return new DefaultQueryDispatcher();
    }
}
