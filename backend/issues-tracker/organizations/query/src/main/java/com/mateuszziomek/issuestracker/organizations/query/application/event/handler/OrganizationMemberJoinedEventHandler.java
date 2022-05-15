package com.mateuszziomek.issuestracker.organizations.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.organizations.query.application.gateway.notification.NotificationGateway;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.organizations.query.domain.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrganizationMemberJoinedEventHandler implements ReactiveEventHandler<OrganizationMemberJoinedEvent> {
    private final OrganizationRepository organizationRepository;
    private final NotificationGateway notificationGateway;

    @Override
    public Mono<Void> handle(OrganizationMemberJoinedEvent event) {
        return organizationRepository
                .findById(event.getId())
                .doOnNext(organization -> organization.joinMember(event))
                .flatMap(organizationRepository::save)
                .doOnNext(organization -> notificationGateway.notify(event, organization).subscribe())
                .then();
    }
}
