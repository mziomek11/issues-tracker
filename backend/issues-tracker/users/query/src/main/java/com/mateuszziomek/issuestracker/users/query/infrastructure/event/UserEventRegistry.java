package com.mateuszziomek.issuestracker.users.query.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.EventDispatcher;
import com.mateuszziomek.issuestracker.users.query.application.event.handler.UserActivatedEventHandler;
import com.mateuszziomek.issuestracker.users.query.application.event.handler.UserRegisteredEventHandler;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.shared.domain.event.UserActivatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
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
