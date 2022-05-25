package com.mateuszziomek.issuestracker.users.query.infrastructure.query;

import com.mateuszziomek.cqrs.query.dispatcher.QueryDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserQueryConfiguration {
    @Bean
    public QueryDispatcher queryDispatcher() {
        return new QueryDispatcher();
    }
}
