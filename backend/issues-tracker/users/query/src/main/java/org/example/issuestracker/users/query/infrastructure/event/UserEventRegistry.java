package org.example.issuestracker.users.query.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.event.dispatcher.EventDispatcher;
import org.example.issuestracker.shared.domain.event.UserActivatedEvent;
import org.example.issuestracker.users.query.application.event.handler.UserActivatedEventHandler;
import org.example.issuestracker.users.query.application.event.handler.UserRegisteredEventHandler;
import org.example.issuestracker.shared.domain.event.UserRegisteredEvent;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class UserEventRegistry {
    private final EventDispatcher eventDispatcher;
    private final UserRegisteredEventHandler userRegisteredEventHandler;
    private final UserActivatedEventHandler userActivatedEventHandler;

    @PostConstruct
    public void registerHandlers() {
        eventDispatcher.registerHandler(UserRegisteredEvent.class, userRegisteredEventHandler);
        eventDispatcher.registerHandler(UserActivatedEvent.class, userActivatedEventHandler);
    }
}
