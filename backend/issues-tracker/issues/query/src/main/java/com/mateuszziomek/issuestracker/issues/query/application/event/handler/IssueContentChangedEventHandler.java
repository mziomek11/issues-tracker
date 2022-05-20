package com.mateuszziomek.issuestracker.issues.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.notification.NotificationGateway;
import com.mateuszziomek.issuestracker.issues.query.domain.issue.IssueRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueContentChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class IssueContentChangedEventHandler implements ReactiveEventHandler<IssueContentChangedEvent> {
    private final IssueRepository issueRepository;
    private final NotificationGateway notificationGateway;

    @Override
    public Mono<Void> handle(IssueContentChangedEvent event) {
        return issueRepository
                .findById(event.getId())
                .doOnNext(issue -> issue.changeContent(event))
                .flatMap(issueRepository::save)
                .doOnNext(issue -> notificationGateway.notify(event, issue).subscribe())
                .then();
    }
}
