package com.mateuszziomek.issuestracker.issues.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.issues.query.domain.issue.IssueRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueCommentVotedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class IssueCommentVotedEventHandler implements ReactiveEventHandler<IssueCommentVotedEvent> {
    private final IssueRepository issueRepository;

    @Override
    public Mono<Void> handle(IssueCommentVotedEvent event) {
        return issueRepository
                .findById(event.getId())
                .doOnNext(issue -> issue.voteComment(event))
                .flatMap(issueRepository::save)
                .then();
    }
}
