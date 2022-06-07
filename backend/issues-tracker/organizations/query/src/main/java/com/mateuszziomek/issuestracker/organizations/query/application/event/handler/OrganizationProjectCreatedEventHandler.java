package com.mateuszziomek.issuestracker.organizations.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.organizations.query.domain.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
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
