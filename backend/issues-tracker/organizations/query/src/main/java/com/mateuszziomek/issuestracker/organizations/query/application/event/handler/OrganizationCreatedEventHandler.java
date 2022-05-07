package com.mateuszziomek.issuestracker.organizations.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.organizations.query.domain.Organization;
import com.mateuszziomek.issuestracker.organizations.query.domain.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrganizationCreatedEventHandler implements ReactiveEventHandler<OrganizationCreatedEvent> {
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public Mono<Void> handle(OrganizationCreatedEvent event) {
        return organizationRepository
                .save(Organization.create(event))
                .then();
    }
}
