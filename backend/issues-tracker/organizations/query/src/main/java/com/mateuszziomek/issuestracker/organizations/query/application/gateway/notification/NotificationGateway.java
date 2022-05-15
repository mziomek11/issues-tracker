package com.mateuszziomek.issuestracker.organizations.query.application.gateway.notification;

import com.mateuszziomek.issuestracker.organizations.query.domain.Organization;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import reactor.core.publisher.Mono;

public interface NotificationGateway {
    Mono<Void> notify(OrganizationCreatedEvent event, Organization organization);
    Mono<Void> notify(OrganizationMemberJoinedEvent event, Organization organization);
    Mono<Void> notify(OrganizationProjectCreatedEvent event, Organization organization);
}
