package com.mateuszziomek.issuestracker.organizations.query.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.DefaultEventDispatcher;
import com.mateuszziomek.cqrs.event.dispatcher.EventDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationEventConfiguration {
    @Bean
    public EventDispatcher eventDispatcher() {
        return new DefaultEventDispatcher();
    }
}
