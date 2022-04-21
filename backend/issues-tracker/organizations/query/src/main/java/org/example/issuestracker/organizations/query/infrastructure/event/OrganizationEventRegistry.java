package org.example.issuestracker.organizations.query.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.event.dispatcher.EventDispatcher;
import org.example.issuestracker.organizations.query.application.event.handler.OrganizationCreatedEventHandler;
import org.example.issuestracker.organizations.query.application.event.handler.OrganizationMemberJoinedEventHandler;
import org.example.issuestracker.organizations.query.application.event.handler.OrganizationProjectCreatedEventHandler;
import org.example.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import org.example.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import org.example.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class OrganizationEventRegistry {
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
