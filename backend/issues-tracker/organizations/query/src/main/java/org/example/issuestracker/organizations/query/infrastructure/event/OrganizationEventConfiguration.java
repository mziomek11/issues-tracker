package org.example.issuestracker.organizations.query.infrastructure.event;

import org.example.cqrs.event.dispatcher.DefaultEventDispatcher;
import org.example.cqrs.event.dispatcher.EventDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationEventConfiguration {
    @Bean
    public EventDispatcher eventDispatcher() {
        return new DefaultEventDispatcher();
    }
}