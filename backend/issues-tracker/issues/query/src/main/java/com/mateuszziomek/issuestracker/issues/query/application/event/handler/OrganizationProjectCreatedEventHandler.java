package com.mateuszziomek.issuestracker.issues.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.issues.query.domain.organization.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrganizationProjectCreatedEventHandler implements ReactiveEventHandler<OrganizationProjectCreatedEvent> {
    private final OrganizationRepository organizationRepository;

    @Override
    public Mono<Void> handle(OrganizationProjectCreatedEvent event) {
        return organizationRepository
                .findById(event.getId())
                .doOnNext(organization -> organization.addProject(event))
                .flatMap(organizationRepository::save)
                .then();
    }
}
