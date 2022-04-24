package com.mateuszziomek.issuestracker.organizations.query.application.event.handler;

import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.event.EventHandler;
import com.mateuszziomek.issuestracker.organizations.query.domain.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrganizationProjectCreatedEventHandler implements EventHandler<OrganizationProjectCreatedEvent> {
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public void handle(OrganizationProjectCreatedEvent event) {
        var optionalOrganization = organizationRepository.findById(event.getId());
        if (optionalOrganization.isEmpty()) {
            return;
        }

        var organization = optionalOrganization.get();
        organization.addProject(event);

        organizationRepository.save(organization);
    }
}
