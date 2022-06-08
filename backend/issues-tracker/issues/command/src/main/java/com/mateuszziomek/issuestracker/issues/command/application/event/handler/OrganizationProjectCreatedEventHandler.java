package com.mateuszziomek.issuestracker.issues.command.application.event.handler;

import com.mateuszziomek.cqrs.event.EventHandler;
import com.mateuszziomek.issuestracker.issues.command.projection.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationProjectCreatedEventHandler implements EventHandler<OrganizationProjectCreatedEvent> {
    private final OrganizationRepository organizationRepository;

    @Override
    public void handle(OrganizationProjectCreatedEvent event) {
        organizationRepository
                .findById(event.getId())
                .ifPresent(organization -> {
                    organization.addProject(event);
                    organizationRepository.save(organization);
                });
    }
}
