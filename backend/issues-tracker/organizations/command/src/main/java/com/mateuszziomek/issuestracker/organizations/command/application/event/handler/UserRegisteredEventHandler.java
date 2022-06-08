package com.mateuszziomek.issuestracker.organizations.command.application.event.handler;

import com.mateuszziomek.cqrs.event.EventHandler;
import com.mateuszziomek.issuestracker.organizations.command.projection.Member;
import com.mateuszziomek.issuestracker.organizations.command.projection.MemberRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventHandler implements EventHandler<UserRegisteredEvent> {
    private final MemberRepository memberRepository;

    @Override
    public void handle(UserRegisteredEvent event) {
        if (memberRepository.findByEmail(event.getUserEmail()).isPresent()) {
            return;
        }

        memberRepository.save(Member.register(event));
    }
}
