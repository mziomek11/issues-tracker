package com.mateuszziomek.issuestracker.issues.query.infrastructure.gateway.notification;

import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.issuestracker.issues.query.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.query.domain.issue.IssueRepository;
import com.mateuszziomek.issuestracker.issues.query.domain.organization.Organization;
import com.mateuszziomek.issuestracker.issues.query.domain.organization.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.*;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification.ReactiveNotificationRestClient;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.notification.UserNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NotificationGateway {
    private final ReactiveNotificationRestClient notificationRestClient;
    private final IssueRepository issueRepository;
    private final OrganizationRepository organizationRepository;

    public Mono<Void> notify(IssueClosedEvent event) {
        return notifyAboutIssueChange(event, event.getMemberId());
    }

    public Mono<Void> notify(IssueCommentContentChangedEvent event) {
        return notifyAboutIssueCommentChange(event, event.getMemberId(), event.getCommentId());
    }

    public Mono<Void> notify(IssueCommentedEvent event) {
        return notifyAboutIssueCommentChange(event, event.getMemberId(), event.getCommentId());
    }

    public Mono<Void> notify(IssueCommentHiddenEvent event) {
        return notifyAboutIssueCommentChange(event, event.getMemberId(), event.getCommentId());
    }

    public Mono<Void> notify(IssueCommentVotedEvent event) {
        return notifyAboutIssueCommentChange(event, event.getMemberId(), event.getCommentId());
    }

    public Mono<Void> notify(IssueContentChangedEvent event) {
        return notifyAboutIssueChange(event, event.getMemberId());
    }

    public Mono<Void> notify(IssueOpenedEvent event) {
        return notifyAboutIssueChange(event, event.getMemberId());
    }

    public Mono<Void> notify(IssueRenamedEvent event) {
        return notifyAboutIssueChange(event, event.getMemberId());
    }

    public Mono<Void> notify(IssueTypeChangedEvent event) {
        return notifyAboutIssueChange(event, event.getMemberId());
    }

    public Mono<Void> notify(IssueVotedEvent event) {
        return notifyAboutIssueChange(event, event.getMemberId());
    }

    private Mono<Void> notifyAboutIssueChange(BaseEvent event, UUID memberId) {
        return getIssueAndOrganization(event)
                .map(tuple -> {
                    var issue = tuple.getT1();
                    var organization = tuple.getT2();

                    return new UserNotificationDto(
                        event.getClass().getSimpleName(),
                        IssueNotification.issue(issue, memberId),
                        organization.getMemberIds()
                    );
                })
                .flatMap(this::notifyUsers);
    }

    private Mono<Void> notifyAboutIssueCommentChange(BaseEvent event, UUID memberId, UUID commentId) {
        return getIssueAndOrganization(event)
                .map(tuple -> {
                    var issue = tuple.getT1();
                    var organization = tuple.getT2();

                    return new UserNotificationDto(
                            event.getClass().getSimpleName(),
                            IssueNotification.issueComment(issue, memberId, commentId),
                            organization.getMemberIds()
                    );
                })
                .flatMap(this::notifyUsers);
    }

    private Mono<Tuple2<Issue, Organization>> getIssueAndOrganization(BaseEvent event) {
        return issueRepository
                .findById(event.getId())
                .flatMap(issue -> Mono.zip(Mono.just(issue), organizationRepository.findById(issue.getOrganizationId())));
    }

    private Mono<Void> notifyUsers(UserNotificationDto notification) {
        return notificationRestClient
                .notifyUsers(notification)
                .onErrorResume(throwable -> Mono.empty());
    }
}
