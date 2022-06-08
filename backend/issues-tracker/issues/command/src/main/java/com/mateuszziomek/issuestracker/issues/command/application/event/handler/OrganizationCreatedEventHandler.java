package com.mateuszziomek.issuestracker.issues.command.application.event.handler;

import com.mateuszziomek.cqrs.event.EventHandler;
import com.mateuszziomek.issuestracker.issues.command.projection.Organization;
import com.mateuszziomek.issuestracker.issues.command.projection.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationCreatedEventHandler implements EventHandler<OrganizationCreatedEvent> {
    private final OrganizationRepository organizationRepository;

    @Override
    public void handle(OrganizationCreatedEvent event) {
        organizationRepository.save(Organization.create(event));
    }
}
