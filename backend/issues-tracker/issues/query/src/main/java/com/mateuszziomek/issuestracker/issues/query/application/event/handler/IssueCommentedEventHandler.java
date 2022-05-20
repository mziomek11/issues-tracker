package com.mateuszziomek.issuestracker.issues.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.issues.query.application.gateway.notification.NotificationGateway;
import com.mateuszziomek.issuestracker.issues.query.domain.comment.Comment;
import com.mateuszziomek.issuestracker.issues.query.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.query.domain.issue.IssueRepository;
import com.mateuszziomek.issuestracker.issues.query.domain.member.Member;
import com.mateuszziomek.issuestracker.issues.query.domain.member.MemberRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueCommentedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Component
@RequiredArgsConstructor
public class IssueCommentedEventHandler implements ReactiveEventHandler<IssueCommentedEvent> {
    private final MemberRepository memberRepository;
    private final IssueRepository issueRepository;
    private final NotificationGateway notificationGateway;

    @Override
    public Mono<Void> handle(IssueCommentedEvent event) {
        return Mono.zip(
                    memberRepository.findById(event.getMemberId()),
                    issueRepository.findById(event.getId())
                )
                .doOnNext(tuple -> commentIssue(event, tuple))
                .map(Tuple2::getT2)
                .flatMap(issueRepository::save)
                .doOnNext(issue -> notificationGateway.notify(event, issue).subscribe())
                .then();
    }

    private void commentIssue(IssueCommentedEvent event, Tuple2<Member, Issue> tuple) {
        var member= tuple.getT1();
        var issue = tuple.getT2();
        var comment = Comment.create(event, member);

        issue.comment(comment);
    }
}
