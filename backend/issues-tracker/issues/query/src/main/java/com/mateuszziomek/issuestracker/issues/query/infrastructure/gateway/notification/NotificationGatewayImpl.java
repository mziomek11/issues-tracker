package com.mateuszziomek.issuestracker.issues.query.infrastructure.gateway.notification;

import com.mateuszziomek.issuestracker.issues.query.application.gateway.notification.NotificationGateway;
import com.mateuszziomek.issuestracker.issues.query.domain.issue.Issue;
import com.mateuszziomek.issuestracker.shared.domain.event.*;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.notification.ReactiveNotificationRestClient;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.ReactiveOrganizationRestClient;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.notification.UserNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationGatewayImpl implements NotificationGateway {
    private static final String ISSUE_CLOSED = "IssueClosed";
    private static final String ISSUE_COMMENT_CONTENT_CHANGED = "IssueCommentContentChanged";
    private static final String ISSUE_COMMENTED = "IssueCommented";
    private static final String ISSUE_COMMENT_HIDDEN = "IssueCommentHidden";
    private static final String ISSUE_COMMENT_VOTED = "IssueCommentVoted";
    private static final String ISSUE_CONTENT_CHANGED = "IssueContentChanged";
    private static final String ISSUE_OPENED = "IssueOpened";
    private static final String ISSUE_RENAMED = "IssueRenamed";
    private static final String ISSUE_TYPE_CHANGED = "IssueTypeChanged";
    private static final String ISSUE_VOTED = "IssueVoted";

    private final ReactiveNotificationRestClient notificationRestClient;
    private final ReactiveOrganizationRestClient organizationRestClient;

    @Override
    public Mono<Void> notify(IssueClosedEvent event, Issue issue) {
        return notifyAboutIssueChange(ISSUE_CLOSED, issue, event.getMemberId());
    }

    @Override
    public Mono<Void> notify(IssueCommentContentChangedEvent event, Issue issue) {
        return notifyAboutIssueCommentChange(
                ISSUE_COMMENT_CONTENT_CHANGED,
                issue,
                event.getMemberId(),
                event.getCommentId()
        );
    }

    @Override
    public Mono<Void> notify(IssueCommentedEvent event, Issue issue) {
        return notifyAboutIssueCommentChange(
                ISSUE_COMMENTED,
                issue,
                event.getMemberId(),
                event.getCommentId()
        );
    }

    @Override
    public Mono<Void> notify(IssueCommentHiddenEvent event, Issue issue) {
        return notifyAboutIssueCommentChange(
                ISSUE_COMMENT_HIDDEN,
                issue,
                event.getMemberId(),
                event.getCommentId()
        );
    }

    @Override
    public Mono<Void> notify(IssueCommentVotedEvent event, Issue issue) {
        return notifyAboutIssueCommentChange(
                ISSUE_COMMENT_VOTED,
                issue,
                event.getMemberId(),
                event.getCommentId()
        );
    }

    @Override
    public Mono<Void> notify(IssueContentChangedEvent event, Issue issue) {
        return notifyAboutIssueChange(ISSUE_CONTENT_CHANGED, issue, event.getMemberId());
    }

    @Override
    public Mono<Void> notify(IssueOpenedEvent event, Issue issue) {
        return notifyAboutIssueChange(ISSUE_OPENED, issue, event.getMemberId());
    }

    @Override
    public Mono<Void> notify(IssueRenamedEvent event, Issue issue) {
        return notifyAboutIssueChange(ISSUE_RENAMED, issue, event.getMemberId());
    }

    @Override
    public Mono<Void> notify(IssueTypeChangedEvent event, Issue issue) {
        return notifyAboutIssueChange(ISSUE_TYPE_CHANGED, issue, event.getMemberId());
    }

    @Override
    public Mono<Void> notify(IssueVotedEvent event, Issue issue) {
        return notifyAboutIssueChange(ISSUE_VOTED, issue, event.getMemberId());
    }

    private Mono<Void> notifyAboutIssueChange(String eventName, Issue issue, UUID memberId) {
        return getOrganizationUserIds(issue)
                .map(ids -> new UserNotificationDto(
                        eventName,
                        IssueNotification.issue(issue, memberId),
                        ids
                ))
                .flatMap(this::notifyUsers);
    }

    private Mono<Void> notifyAboutIssueCommentChange(String eventName, Issue issue, UUID memberId, UUID commentId) {
        return getOrganizationUserIds(issue)
                .map(ids -> new UserNotificationDto(
                        eventName,
                        IssueNotification.issueComment(issue, memberId, commentId),
                        ids
                ))
                .flatMap(this::notifyUsers);
    }

    private Mono<Set<UUID>> getOrganizationUserIds(Issue issue) {
        return organizationRestClient
                .getOrganizationById(issue.getOrganizationId())
                .map(organization -> organization
                        .getMembers()
                        .stream()
                        .map(DetailsOrganization.Member::getId)
                        .collect(Collectors.toSet())
                );
    }

    private Mono<Void> notifyUsers(UserNotificationDto notification) {
        return notificationRestClient
                .notifyUsers(notification)
                .onErrorResume(throwable -> Mono.empty());
    }
}
