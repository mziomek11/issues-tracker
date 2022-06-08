package com.mateuszziomek.issuestracker.issues.command.application.event.handler;

import com.mateuszziomek.cqrs.event.EventHandler;
import com.mateuszziomek.issuestracker.issues.command.projection.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationMemberJoinedEventHandler implements EventHandler<OrganizationMemberJoinedEvent> {
    private final OrganizationRepository organizationRepository;

    @Override
    public void handle(OrganizationMemberJoinedEvent event) {
        organizationRepository
                .findById(event.getId())
                .ifPresent(organization -> {
                    organization.joinMember(event);
                    organizationRepository.save(organization);
                });
    }
}
