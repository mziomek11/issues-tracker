package com.mateuszziomek.issuestracker.organizations.query.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.ReactiveEventDispatcher;
import com.mateuszziomek.issuestracker.organizations.query.application.event.handler.OrganizationCreatedEventHandler;
import com.mateuszziomek.issuestracker.organizations.query.application.event.handler.OrganizationMemberJoinedEventHandler;
import com.mateuszziomek.issuestracker.organizations.query.application.event.handler.OrganizationProjectCreatedEventHandler;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class OrganizationEventRegistry {
    private final ReactiveEventDispatcher eventDispatcher;
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
