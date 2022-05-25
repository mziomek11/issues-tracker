package com.mateuszziomek.issuestracker.notifications.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.ReactiveEventDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationEventConfiguration {

    @Bean
    public ReactiveEventDispatcher eventDispatcher() {
        return new ReactiveEventDispatcher();
    }
}
