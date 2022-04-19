package org.example.issuestracker.users.query.infrastructure.event;

import org.example.cqrs.event.dispatcher.DefaultEventDispatcher;
import org.example.cqrs.event.dispatcher.EventDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserEventConfiguration {
    @Bean
    public EventDispatcher eventDispatcher() {
        return new DefaultEventDispatcher();
    }
}
