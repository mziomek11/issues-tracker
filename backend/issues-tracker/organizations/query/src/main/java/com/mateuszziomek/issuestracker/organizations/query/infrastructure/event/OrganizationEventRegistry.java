package com.mateuszziomek.issuestracker.organizations.query.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.ReactiveEventDispatcher;
import com.mateuszziomek.issuestracker.organizations.query.application.event.handler.*;
import com.mateuszziomek.issuestracker.shared.domain.event.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class OrganizationEventRegistry {
    private final ReactiveEventDispatcher eventDispatcher;
    private final OrganizationCreatedEventHandler organizationCreatedEventHandler;
    private final OrganizationMemberInvitedEventHandler organizationMemberInvitedEventHandler;
    private final OrganizationMemberJoinedEventHandler organizationMemberJoinedEventHandler;
    private final OrganizationProjectCreatedEventHandler organizationProjectCreatedEventHandler;
    private final UserRegisteredEventHandler userRegisteredEventHandler;

    @PostConstruct
    public void registerHandlers() {
        eventDispatcher.registerHandler(OrganizationCreatedEvent.class, organizationCreatedEventHandler);
        eventDispatcher.registerHandler(OrganizationMemberInvitedEvent.class, organizationMemberInvitedEventHandler);
        eventDispatcher.registerHandler(OrganizationMemberJoinedEvent.class, organizationMemberJoinedEventHandler);
        eventDispatcher.registerHandler(OrganizationProjectCreatedEvent.class, organizationProjectCreatedEventHandler);
        eventDispatcher.registerHandler(UserRegisteredEvent.class, userRegisteredEventHandler);
    }
}
