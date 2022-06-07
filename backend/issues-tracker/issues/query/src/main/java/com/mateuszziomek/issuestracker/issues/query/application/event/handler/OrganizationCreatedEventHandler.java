package com.mateuszziomek.issuestracker.issues.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.issues.query.domain.organization.Organization;
import com.mateuszziomek.issuestracker.issues.query.domain.organization.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrganizationCreatedEventHandler implements ReactiveEventHandler<OrganizationCreatedEvent> {
    private final OrganizationRepository organizationRepository;

    @Override
    public Mono<Void> handle(OrganizationCreatedEvent event) {
        return organizationRepository
                .save(Organization.create(event))
                .then();
    }
}
