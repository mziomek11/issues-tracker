package com.mateuszziomek.issuestracker.issues.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.issues.query.domain.IssueRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueTypeChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class IssueTypeChangedEventHandler implements ReactiveEventHandler<IssueTypeChangedEvent> {
    private final IssueRepository issueRepository;

    @Override
    public Mono<Void> handle(IssueTypeChangedEvent event) {
        return issueRepository
                .findById(event.getId())
                .doOnNext(issue -> issue.changeType(event))
                .flatMap(issueRepository::save)
                .then();
    }
}
