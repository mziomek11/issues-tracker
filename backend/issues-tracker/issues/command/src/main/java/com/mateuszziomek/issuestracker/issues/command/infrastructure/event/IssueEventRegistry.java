package com.mateuszziomek.issuestracker.issues.command.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.EventDispatcher;
import com.mateuszziomek.issuestracker.issues.command.application.event.handler.OrganizationCreatedEventHandler;
import com.mateuszziomek.issuestracker.issues.command.application.event.handler.OrganizationMemberJoinedEventHandler;
import com.mateuszziomek.issuestracker.issues.command.application.event.handler.OrganizationProjectCreatedEventHandler;
import com.mateuszziomek.issuestracker.shared.domain.event.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class IssueEventRegistry {
    private final EventDispatcher eventDispatcher;
    private final OrganizationCreatedEventHandler organizationCreatedEventHandler;
    private final OrganizationMemberJoinedEventHandler organizationMemberJoinedEventHandler;
    private final OrganizationProjectCreatedEventHandler organizationProjectCreatedEventHandler;

    @PostConstruct
    public void registerHandlers() {
        eventDispatcher.registerHandler(OrganizationCreatedEvent.class, organizationCreatedEventHandler);
        eventDispatcher.registerHandler(OrganizationMemberJoinedEvent.class, organizationMemberJoinedEventHandler);
        eventDispatcher.registerHandler(OrganizationProjectCreatedEvent.class, organizationProjectCreatedEventHandler);
    }
}
