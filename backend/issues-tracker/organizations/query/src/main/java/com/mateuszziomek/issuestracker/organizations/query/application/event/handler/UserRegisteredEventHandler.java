package com.mateuszziomek.issuestracker.organizations.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.organizations.query.domain.member.Member;
import com.mateuszziomek.issuestracker.organizations.query.domain.member.MemberRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventHandler implements ReactiveEventHandler<UserRegisteredEvent> {
    private final MemberRepository memberRepository;

    @Override
    public Mono<Void> handle(UserRegisteredEvent event) {
        return memberRepository
                .save(Member.create(event))
                .then();
    }
}
