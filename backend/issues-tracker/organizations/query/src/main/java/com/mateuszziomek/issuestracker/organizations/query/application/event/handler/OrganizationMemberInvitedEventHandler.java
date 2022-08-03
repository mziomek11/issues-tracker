package com.mateuszziomek.issuestracker.organizations.query.application.event.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import com.mateuszziomek.issuestracker.organizations.query.domain.invitation.Invitation;
import com.mateuszziomek.issuestracker.organizations.query.domain.invitation.InvitationRepository;
import com.mateuszziomek.issuestracker.organizations.query.domain.organization.Organization;
import com.mateuszziomek.issuestracker.organizations.query.domain.organization.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OrganizationMemberInvitedEventHandler implements ReactiveEventHandler<OrganizationMemberInvitedEvent> {
    private final OrganizationRepository organizationRepository;
    private final InvitationRepository invitationRepository;

    @Override
    public Mono<Void> handle(OrganizationMemberInvitedEvent event) {
        return organizationRepository
                .findById(event.getId())
                .flatMap(organization -> invitationRepository
                        .save(createInvitation(event, organization))
                        .map(invitation -> organization)
                )
                .then();
    }

    private Invitation createInvitation(OrganizationMemberInvitedEvent event, Organization organization) {
        return new Invitation(organization.getId(), organization.getName(), event.getMemberId());
    }
}
