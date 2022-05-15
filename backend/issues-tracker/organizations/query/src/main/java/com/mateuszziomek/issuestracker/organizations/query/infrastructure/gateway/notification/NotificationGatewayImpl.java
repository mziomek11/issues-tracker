package com.mateuszziomek.issuestracker.organizations.query.infrastructure.gateway.notification;

import com.mateuszziomek.issuestracker.organizations.query.application.gateway.notification.NotificationGateway;
import com.mateuszziomek.issuestracker.organizations.query.domain.Member;
import com.mateuszziomek.issuestracker.organizations.query.domain.Organization;
import com.mateuszziomek.issuestracker.shared.ui.notification.UserNotification;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification.NotificationRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class NotificationGatewayImpl implements NotificationGateway {
    private static final String ORGANIZATION_CREATED = "OrganizationCreated";
    private static final String ORGANIZATION_MEMBER_JOINED = "OrganizationMemberJoined";
    private static final String ORGANIZATION_PROJECT_CREATED = "OrganizationProjectCreated";

    private final NotificationRestClient notificationRestClient;

    @Override
    public Mono<Void> notify(OrganizationCreatedEvent event, Organization organization) {
        var notification = new UserNotification(
                ORGANIZATION_CREATED,
                OrganizationNotification.created(event),
                getOrganizationUsers(organization)
        );

        return notifyUsers(notification);
    }

    @Override
    public Mono<Void> notify(OrganizationMemberJoinedEvent event, Organization organization) {
        var notification = new UserNotification(
                ORGANIZATION_MEMBER_JOINED,
                OrganizationNotification.memberJoined(event),
                getOrganizationUsers(organization)
        );

        return notifyUsers(notification);
    }

    @Override
    public Mono<Void> notify(OrganizationProjectCreatedEvent event, Organization organization) {
        var notification = new UserNotification(
                ORGANIZATION_PROJECT_CREATED,
                OrganizationNotification.projectCreated(event),
                getOrganizationUsers(organization)
        );

        return notifyUsers(notification);
    }

    private Mono<Void> notifyUsers(UserNotification notification) {
        return notificationRestClient
                .notifyUsers(notification)
                .onErrorResume(throwable -> Mono.empty());
    }

    private Set<UUID> getOrganizationUsers(Organization organization) {
        return organization
                .getMembers()
                .stream()
                .map(Member::getId)
                .collect(Collectors.toSet());
    }
}
