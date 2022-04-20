package org.example.issuestracker.organizations.command.domain.organization;

import lombok.NoArgsConstructor;
import org.example.cqrs.domain.AggregateRoot;
import org.example.issuestracker.organizations.command.domain.invitation.Invitation;
import org.example.issuestracker.organizations.command.domain.invitation.Invitations;
import org.example.issuestracker.organizations.command.domain.invitation.exception.InvitationAlreadyPresentException;
import org.example.issuestracker.organizations.command.domain.member.Member;
import org.example.issuestracker.organizations.command.domain.member.MemberId;
import org.example.issuestracker.organizations.command.domain.member.Members;
import org.example.issuestracker.organizations.command.domain.member.exception.MemberAlreadyPresentException;
import org.example.issuestracker.organizations.command.domain.organization.exception.OrganizationActionCalledNotByOwnerException;
import org.example.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import org.example.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;

import static org.example.issuestracker.organizations.command.domain.EventFactory.*;

@NoArgsConstructor
public class Organization extends AggregateRoot {
    private OrganizationId id;
    private OrganizationOwner owner;
    private OrganizationName name;
    private Members members;
    private Invitations invitations;

    public static Organization create(OrganizationId id, OrganizationOwner owner, OrganizationName name) {
        var organization = new Organization();

        organization.raiseEvent(organizationCreated(id, owner.getMemberId(), name));

        return organization;
    }

    /**
     * Invites member to organization
     *
     * @param ownerId of organization
     * @param memberId to be invited
     * @throws InvitationAlreadyPresentException see {@link Invitations#ensureCanAdd(Invitation)}
     * @throws MemberAlreadyPresentException see {@link Members#ensureCanAdd(Member)}
     * @throws OrganizationActionCalledNotByOwnerException if action is called not by owner of the organization
     */
    public void invite(MemberId ownerId, MemberId memberId) {
        if (!owner.equals(new OrganizationOwner(ownerId))) {
            throw new OrganizationActionCalledNotByOwnerException(ownerId);
        }

        var member = new Member(memberId);
        var invitation = new Invitation(member);

        members.ensureCanAdd(member);
        invitations.ensureCanAdd(invitation);

        raiseEvent(organizationMemberInvited(id, memberId));
    }

    @Override
    public OrganizationId getId() {
        return id;
    }

    public void on(OrganizationCreatedEvent organizationCreatedEvent) {
        id = new OrganizationId(organizationCreatedEvent.getId());
        owner = new OrganizationOwner(new MemberId(organizationCreatedEvent.getOrganizationOwnerId()));
        name = new OrganizationName(organizationCreatedEvent.getOrganizationName());
        members = new Members(owner);
        invitations = new Invitations();
    }

    public void on(OrganizationMemberInvitedEvent organizationMemberInvitedEvent) {
        var member = new Member(new MemberId(organizationMemberInvitedEvent.getMemberId()));
        invitations = invitations.add(new Invitation(member));
    }
}
