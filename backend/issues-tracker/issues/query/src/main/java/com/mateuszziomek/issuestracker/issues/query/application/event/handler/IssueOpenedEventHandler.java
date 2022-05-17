package com.mateuszziomek.issuestracker.issues.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.issues.query.domain.Issue;
import com.mateuszziomek.issuestracker.issues.query.domain.IssueRepository;
import com.mateuszziomek.issuestracker.issues.query.domain.MemberRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueOpenedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class IssueOpenedEventHandler implements ReactiveEventHandler<IssueOpenedEvent> {
    private final MemberRepository memberRepository;
    private final IssueRepository issueRepository;

    @Override
    public Mono<Void> handle(IssueOpenedEvent event) {
        return memberRepository
                .findById(event.getMemberId())
                .map(member -> Issue.create(event, member))
                .flatMap(issueRepository::save)
                .then();
    }
}
