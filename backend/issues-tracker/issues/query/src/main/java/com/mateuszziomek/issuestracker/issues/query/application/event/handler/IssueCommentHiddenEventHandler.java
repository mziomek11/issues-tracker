package com.mateuszziomek.issuestracker.issues.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.issues.query.domain.issue.IssueRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueCommentHiddenEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class IssueCommentHiddenEventHandler implements ReactiveEventHandler<IssueCommentHiddenEvent> {
    private final IssueRepository issueRepository;

    @Override
    public Mono<Void> handle(IssueCommentHiddenEvent event) {
        return issueRepository
                .findById(event.getId())
                .doOnNext(issue -> issue.hideComment(event))
                .flatMap(issueRepository::save)
                .then();
    }
}
