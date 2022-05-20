package com.mateuszziomek.issuestracker.issues.query.application.gateway.notification;

import com.mateuszziomek.issuestracker.issues.query.domain.issue.Issue;
import com.mateuszziomek.issuestracker.shared.domain.event.*;
import reactor.core.publisher.Mono;

public interface NotificationGateway {
    Mono<Void> notify(IssueClosedEvent event, Issue issue);
    Mono<Void> notify(IssueCommentContentChangedEvent event, Issue issue);
    Mono<Void> notify(IssueCommentedEvent event, Issue issue);
    Mono<Void> notify(IssueCommentHiddenEvent event, Issue issue);
    Mono<Void> notify(IssueCommentVotedEvent event, Issue issue);
    Mono<Void> notify(IssueContentChangedEvent event, Issue issue);
    Mono<Void> notify(IssueOpenedEvent event, Issue issue);
    Mono<Void> notify(IssueRenamedEvent event, Issue issue);
    Mono<Void> notify(IssueTypeChangedEvent event, Issue issue);
    Mono<Void> notify(IssueVotedEvent event, Issue issue);
}
