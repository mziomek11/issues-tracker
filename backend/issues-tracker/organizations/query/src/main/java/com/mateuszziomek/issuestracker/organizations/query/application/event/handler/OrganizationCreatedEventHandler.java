package com.mateuszziomek.issuestracker.organizations.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.organizations.query.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.organizations.query.domain.organization.Organization;
import com.mateuszziomek.issuestracker.organizations.query.domain.organization.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrganizationCreatedEventHandler implements ReactiveEventHandler<OrganizationCreatedEvent> {
    private final MemberRepository memberRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    public Mono<Void> handle(OrganizationCreatedEvent event) {
        return memberRepository
                .findById(event.getOrganizationOwnerId())
                .map(member -> Organization.create(event, member))
                .flatMap(organizationRepository::save)
                .then();
    }
}
