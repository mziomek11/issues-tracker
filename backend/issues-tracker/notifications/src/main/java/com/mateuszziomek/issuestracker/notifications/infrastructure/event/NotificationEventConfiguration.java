package com.mateuszziomek.issuestracker.notifications.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.DefaultEventDispatcher;
import com.mateuszziomek.cqrs.event.dispatcher.EventDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationEventConfiguration {

    @Bean
    public EventDispatcher eventDispatcher() {
        return new DefaultEventDispatcher();
    }
}
