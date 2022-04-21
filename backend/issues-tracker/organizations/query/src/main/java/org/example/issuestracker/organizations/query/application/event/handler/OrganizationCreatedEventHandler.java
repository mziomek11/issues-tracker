package org.example.issuestracker.organizations.query.application.event.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.event.EventHandler;
import org.example.issuestracker.organizations.query.domain.Organization;
import org.example.issuestracker.organizations.query.domain.OrganizationRepository;
import org.example.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationCreatedEventHandler implements EventHandler<OrganizationCreatedEvent> {
    private final OrganizationRepository organizationRepository;

    @Override
    public void handle(OrganizationCreatedEvent event) {
        var organization = Organization.create(event);

        organizationRepository.save(organization);
    }
}
