package org.example.issuestracker.organizations.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.organizations.command.application.command.InviteOrganizationMemberCommand;
import org.example.issuestracker.organizations.command.application.gateway.member.MemberGateway;
import org.example.issuestracker.organizations.command.application.gateway.member.exception.MemberNotFoundException;
import org.example.issuestracker.organizations.command.domain.invitation.Invitation;
import org.example.issuestracker.organizations.command.domain.invitation.exception.InvitationAlreadyPresentException;
import org.example.issuestracker.organizations.command.domain.member.Member;
import org.example.issuestracker.organizations.command.domain.member.MemberEmail;
import org.example.issuestracker.organizations.command.domain.member.exception.MemberAlreadyPresentException;
import org.example.issuestracker.organizations.command.domain.organization.Organization;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationOwner;
import org.example.issuestracker.organizations.command.domain.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.organizations.command.domain.organization.exception.OrganizationOwnerNotValidException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InviteOrganizationMemberCommandHandler implements CommandHandler<InviteOrganizationMemberCommand> {
    private final EventSourcingHandler<Organization> eventSourcingHandler;
    private final MemberGateway memberGateway;

    /**
     * @throws InvitationAlreadyPresentException see {@link Organization#invite(OrganizationOwner, Invitation)}
     * @throws MemberAlreadyPresentException see {@link Organization#invite(OrganizationOwner, Invitation)}
     * @throws MemberNotFoundException see {@link MemberGateway#getMemberId(MemberEmail)}
     * @throws OrganizationOwnerNotValidException see {@link Organization#invite(OrganizationOwner, Invitation)}
     */
    @Override
    public void handle(InviteOrganizationMemberCommand command) {
        var organization = eventSourcingHandler
                .getById(command.getOrganizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(command.getOrganizationId()));

        var memberId = memberGateway.getMemberId(command.getMemberEmail());

        var organizationOwner = new OrganizationOwner(command.getOrganizationOwnerId());
        var invitation = new Invitation(new Member((memberId)));

        organization.invite(organizationOwner, invitation);
        eventSourcingHandler.save(organization);
    }
}
