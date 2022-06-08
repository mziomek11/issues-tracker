package com.mateuszziomek.issuestracker.organizations.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.organizations.command.application.service.MemberService;
import com.mateuszziomek.issuestracker.organizations.command.application.service.exception.MemberNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.exception.MemberAlreadyPresentException;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationOwner;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.issuestracker.organizations.command.application.command.InviteOrganizationMemberCommand;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.Invitation;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception.InvitationAlreadyPresentException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.Member;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberEmail;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.Organization;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationOwnerNotValidException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InviteOrganizationMemberCommandHandler implements CommandHandler<InviteOrganizationMemberCommand> {
    private final EventSourcingHandler<Organization> eventSourcingHandler;
    private final MemberService memberService;

    /**
     * @throws InvitationAlreadyPresentException see {@link Organization#invite(OrganizationOwner, Invitation)}
     * @throws MemberAlreadyPresentException see {@link Organization#invite(OrganizationOwner, Invitation)}
     * @throws MemberNotFoundException see {@link MemberService#getMemberId(MemberEmail)}
     * @throws OrganizationNotFoundException if organization with given id does not exist
     * @throws OrganizationOwnerNotValidException see {@link Organization#invite(OrganizationOwner, Invitation)}
     */
    @Override
    public void handle(InviteOrganizationMemberCommand command) {
        var organization = eventSourcingHandler
                .getById(command.getOrganizationId())
                .orElseThrow(() -> new OrganizationNotFoundException(command.getOrganizationId()));

        var memberId = memberService.getMemberId(command.getMemberEmail());
        var organizationOwner = new OrganizationOwner(command.getOrganizationOwnerId());
        var invitation = new Invitation(new Member((memberId)));

        organization.invite(organizationOwner, invitation);
        eventSourcingHandler.save(organization);
    }
}
