package com.mateuszziomek.issuestracker.organizations.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.organizations.query.domain.invitation.InvitationRepository;
import com.mateuszziomek.issuestracker.organizations.query.domain.member.MemberRepository;
import com.mateuszziomek.issuestracker.organizations.query.domain.organization.Organization;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.organizations.query.domain.organization.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrganizationMemberJoinedEventHandler implements ReactiveEventHandler<OrganizationMemberJoinedEvent> {
    private final OrganizationRepository organizationRepository;
    private final InvitationRepository invitationRepository;
    private final MemberRepository memberRepository;

    @Override
    public Mono<Void> handle(OrganizationMemberJoinedEvent event) {
        var organizationMono = organizationRepository.findById(event.getId());
        var memberMono = memberRepository.findById(event.getMemberId());

        return Mono.zip(organizationMono, memberMono)
                .doOnNext(zip -> {
                    var organization = zip.getT1();
                    var member = zip.getT2();

                    organization.joinMember(member);
                })
                .map(zip -> zip.getT1())
                .flatMap(organizationRepository::save)
                .flatMap(organization -> invitationRepository.deleteById(organization.getId()))
                .then();
    }
}
