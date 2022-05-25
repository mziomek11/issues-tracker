package com.mateuszziomek.issuestracker.organizations.query.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.ReactiveEventDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationEventConfiguration {
    @Bean
    public ReactiveEventDispatcher eventDispatcher() {
        return new ReactiveEventDispatcher();
    }
}
