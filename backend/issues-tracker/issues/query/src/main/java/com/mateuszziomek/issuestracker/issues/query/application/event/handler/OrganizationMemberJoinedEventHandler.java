package com.mateuszziomek.issuestracker.issues.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.issues.query.domain.organization.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrganizationMemberJoinedEventHandler implements ReactiveEventHandler<OrganizationMemberJoinedEvent> {
    private final OrganizationRepository organizationRepository;

    @Override
    public Mono<Void> handle(OrganizationMemberJoinedEvent event) {
        return organizationRepository
                .findById(event.getId())
                .doOnNext(organization -> organization.joinMember(event))
                .flatMap(organizationRepository::save)
                .then();
    }
}
