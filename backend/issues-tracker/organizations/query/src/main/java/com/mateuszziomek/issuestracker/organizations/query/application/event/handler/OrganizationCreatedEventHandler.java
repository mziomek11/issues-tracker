package com.mateuszziomek.issuestracker.organizations.query.application.event.handler;

import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.event.EventHandler;
import com.mateuszziomek.issuestracker.organizations.query.domain.Organization;
import com.mateuszziomek.issuestracker.organizations.query.domain.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrganizationCreatedEventHandler implements EventHandler<OrganizationCreatedEvent> {
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public void handle(OrganizationCreatedEvent event) {
        var organization = Organization.create(event);

        organizationRepository.save(organization);
    }
}
