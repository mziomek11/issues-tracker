package com.mateuszziomek.issuestracker.organizations.query.infrastructure.query;

import com.mateuszziomek.cqrs.query.dispatcher.DefaultQueryDispatcher;
import com.mateuszziomek.cqrs.query.dispatcher.QueryDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationQueryConfiguration {
    @Bean
    public QueryDispatcher queryDispatcher() {
        return new DefaultQueryDispatcher();
    }
}
