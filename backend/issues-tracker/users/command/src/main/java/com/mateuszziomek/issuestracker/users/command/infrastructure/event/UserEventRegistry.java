package com.mateuszziomek.issuestracker.users.command.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.EventDispatcher;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import com.mateuszziomek.issuestracker.users.command.application.event.handler.UserRegisteredEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class UserEventRegistry {
    private final EventDispatcher eventDispatcher;
    private final UserRegisteredEventHandler userRegisteredEventHandler;

    @PostConstruct
    public void registerHandlers() {
        eventDispatcher.registerHandler(UserRegisteredEvent.class, userRegisteredEventHandler);
    }
}