package org.example.issuestracker.organizations.command.domain.organization;

import lombok.NoArgsConstructor;
import org.example.cqrs.domain.AggregateRoot;
import org.example.issuestracker.organizations.command.domain.invitation.Invitation;
import org.example.issuestracker.organizations.command.domain.invitation.Invitations;
import org.example.issuestracker.organizations.command.domain.invitation.exception.InvitationAlreadyPresentException;
import org.example.issuestracker.organizations.command.domain.invitation.exception.InvitationNotFoundException;
import org.example.issuestracker.organizations.command.domain.member.Member;
import org.example.issuestracker.organizations.command.domain.member.MemberId;
import org.example.issuestracker.organizations.command.domain.member.Members;
import org.example.issuestracker.organizations.command.domain.member.exception.MemberAlreadyPresentException;
import org.example.issuestracker.organizations.command.domain.organization.exception.OrganizationOwnerNotValidException;
import org.example.issuestracker.organizations.command.domain.project.Project;
import org.example.issuestracker.organizations.command.domain.project.ProjectId;
import org.example.issuestracker.organizations.command.domain.project.ProjectName;
import org.example.issuestracker.organizations.command.domain.project.Projects;
import org.example.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import org.example.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;
import org.example.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import org.example.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;

import static org.example.issuestracker.organizations.command.domain.EventFactory.*;

@NoArgsConstructor
public class Organization extends AggregateRoot {
    private OrganizationId id;
    private OrganizationOwner owner;
    private OrganizationName name;
    private Members members;
    private Invitations invitations;
    private Projects projects;

    public static Organization create(OrganizationId id, OrganizationOwner owner, OrganizationName name) {
        var organization = new Organization();

        organization.raiseEvent(organizationCreated(id, owner.getId(), name));

        return organization;
    }

    /**
     * Invites member to organization
     *
     * @param owner of current organization
     * @param invitation to be saved
     * @throws InvitationAlreadyPresentException see {@link Invitations#ensureCanAdd(Invitation)}
     * @throws MemberAlreadyPresentException see {@link Members#ensureCanAdd(Member)}
     * @throws OrganizationOwnerNotValidException see {@link #ensureOrganizationOwnerIsValid(OrganizationOwner)}
     */
    public void invite(OrganizationOwner owner, Invitation invitation) {
        ensureOrganizationOwnerIsValid(owner);
        members.ensureCanAdd(invitation.getMember());
        invitations.ensureCanAdd(invitation);

        raiseEvent(organizationMemberInvited(id, invitation.getMember().getId()));
    }

    /**
     * Accepts invitation
     *
     * @param invitation to be accepted
     * @throws InvitationNotFoundException see {@link Invitations#ensureCanRemove(Invitation)}
     */
    public void acceptInvitation(Invitation invitation) {
        invitations.ensureCanRemove(invitation);

        raiseEvent(organizationMemberJoined(id, invitation.getMember().getId()));
    }

    /**
     * Adds project to organization
     *
     * @param owner of current organization
     * @param project to be added
     * @throws OrganizationOwnerNotValidException see {@link #ensureOrganizationOwnerIsValid(OrganizationOwner)}
     */
    public void addProject(OrganizationOwner owner, Project project) {
        ensureOrganizationOwnerIsValid(owner);

        raiseEvent(organizationProjectCreated(id, project.id(), project.name()));
    }

    /**
     * Ensures that given member is organization owner
     *
     * @param owner to be checked
     * @throws OrganizationOwnerNotValidException if owner is not owning current organization
     */
    private void ensureOrganizationOwnerIsValid(OrganizationOwner owner) {
        if(!this.owner.equals(owner)) {
            throw new OrganizationOwnerNotValidException(owner.getId());
        }
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
        projects = new Projects();
    }

    public void on(OrganizationMemberInvitedEvent organizationMemberInvitedEvent) {
        var member = new Member(new MemberId(organizationMemberInvitedEvent.getMemberId()));
        invitations = invitations.add(new Invitation(member));
    }

    public void on(OrganizationMemberJoinedEvent organizationMemberJoinedEvent) {
        var member = new Member(new MemberId(organizationMemberJoinedEvent.getMemberId()));
        var invitation = new Invitation(member);

        members = members.add(member);
        invitations = invitations.remove(invitation);
    }

    public void on(OrganizationProjectCreatedEvent organizationProjectCreatedEvent) {
        var projectId = new ProjectId(organizationProjectCreatedEvent.getProjectId());
        var projectName = new ProjectName(organizationProjectCreatedEvent.getProjectName());
        var project = new Project(projectId, projectName);

        projects = projects.add(project);
    }
}
