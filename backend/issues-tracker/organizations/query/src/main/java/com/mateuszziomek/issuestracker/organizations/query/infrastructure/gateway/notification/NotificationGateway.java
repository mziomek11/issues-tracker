package com.mateuszziomek.issuestracker.organizations.query.infrastructure.gateway.notification;

import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.issuestracker.organizations.query.domain.member.Member;
import com.mateuszziomek.issuestracker.organizations.query.domain.organization.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.notification.UserNotificationDto;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification.ReactiveNotificationRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class NotificationGateway {
    private final ReactiveNotificationRestClient notificationRestClient;
    private final OrganizationRepository organizationRepository;

    public Mono<Void> notify(OrganizationCreatedEvent event) {
        return notifyAboutOrganizationChange(event, OrganizationNotification.created(event));
    }

    public Mono<Void> notify(OrganizationMemberInvitedEvent event) {
        return notifyAboutOrganizationChange(event, OrganizationNotification.memberInvited(event));
    }

    public Mono<Void> notify(OrganizationMemberJoinedEvent event) {
        return notifyAboutOrganizationChange(event, OrganizationNotification.memberJoined(event));
    }

    public Mono<Void> notify(OrganizationProjectCreatedEvent event) {
        return notifyAboutOrganizationChange(event, OrganizationNotification.projectCreated(event));
    }

    private Mono<Void> notifyAboutOrganizationChange(BaseEvent event, Map<String, ? extends Object> notificationData) {
        return getOrganizationUsers(event)
                .map(users -> new UserNotificationDto(
                        event.getClass().getSimpleName(),
                        notificationData,
                        users
                ))
                .flatMap(this::notifyUsers);
    }

    private Mono<Void> notifyUsers(UserNotificationDto notification) {
        return notificationRestClient
                .notifyUsers(notification)
                .onErrorResume(throwable -> Mono.empty());
    }

    private Mono<Set<UUID>> getOrganizationUsers(BaseEvent event) {
        return organizationRepository.findById(event.getId())
                .map(organization -> organization.getMembers().stream().map(Member::getId).collect(Collectors.toSet()));
    }
}
