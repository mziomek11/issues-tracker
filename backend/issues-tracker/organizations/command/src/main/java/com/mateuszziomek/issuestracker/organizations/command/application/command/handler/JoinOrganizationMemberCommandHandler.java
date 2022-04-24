package com.mateuszziomek.issuestracker.organizations.command.application.command.handler;

import com.mateuszziomek.issuestracker.organizations.command.application.command.JoinOrganizationMemberCommand;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.Member;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.Invitation;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception.InvitationNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.Organization;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationNotFoundException;
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
