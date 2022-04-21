package org.example.issuestracker.organizations.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.organizations.command.application.command.JoinOrganizationMemberCommand;
import org.example.issuestracker.organizations.command.domain.invitation.Invitation;
import org.example.issuestracker.organizations.command.domain.invitation.exception.InvitationNotFoundException;
import org.example.issuestracker.organizations.command.domain.member.Member;
import org.example.issuestracker.organizations.command.domain.organization.Organization;
import org.example.issuestracker.organizations.command.domain.organization.exception.OrganizationNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JoinOrganizationMemberCommandHandler implements CommandHandler<JoinOrganizationMemberCommand> {
    private final EventSourcingHandler<Organization> eventSourcingHandler;

    /**
     * @throws InvitationNotFoundException see {@link Organization#acceptInvitation(Invitation)}
     * @throws OrganizationNotFoundException if organization with given id does not exist
     */
    @Override
    public void handle(JoinOrganizationMemberCommand command) {
        var organization = eventSourcingHandler
                .getById(command.getOrganizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(command.getOrganizationId()));

        var invitation = new Invitation(new Member(command.getMemberId()));
        organization.acceptInvitation(invitation);

        eventSourcingHandler.save(organization);
    }
}
