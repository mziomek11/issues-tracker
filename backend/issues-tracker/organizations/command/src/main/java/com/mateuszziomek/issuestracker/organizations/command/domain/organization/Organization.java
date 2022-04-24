package com.mateuszziomek.issuestracker.organizations.command.domain.organization;

import com.mateuszziomek.issuestracker.organizations.command.domain.EventFactory;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.Member;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.exception.MemberAlreadyPresentException;
import com.mateuszziomek.issuestracker.organizations.command.domain.project.ProjectId;
import com.mateuszziomek.issuestracker.organizations.command.domain.project.ProjectName;
import lombok.NoArgsConstructor;
import com.mateuszziomek.cqrs.domain.AggregateRoot;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.Invitation;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.Invitations;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception.InvitationAlreadyPresentException;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception.InvitationNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.Members;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationOwnerNotValidException;
import com.mateuszziomek.issuestracker.organizations.command.domain.project.Project;
import com.mateuszziomek.issuestracker.organizations.command.domain.project.Projects;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;

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

        organization.raiseEvent(EventFactory.organizationCreated(id, owner.getId(), name));

        return organization;
    }

    /**
     * @throws InvitationAlreadyPresentException see {@link Invitations#ensureCanAdd(Invitation)}
     * @throws MemberAlreadyPresentException see {@link Members#ensureCanAdd(Member)}
     * @throws OrganizationOwnerNotValidException see {@link #ensureOrganizationOwnerIsValid(OrganizationOwner)}
     */
    public void invite(OrganizationOwner owner, Invitation invitation) {
        ensureOrganizationOwnerIsValid(owner);
        members.ensureCanAdd(invitation.getMember());
        invitations.ensureCanAdd(invitation);

        raiseEvent(EventFactory.organizationMemberInvited(id, invitation.getMember().getId()));
    }

    /**
     * @throws InvitationNotFoundException see {@link Invitations#ensureCanRemove(Invitation)}
     */
    public void acceptInvitation(Invitation invitation) {
        invitations.ensureCanRemove(invitation);

        raiseEvent(EventFactory.organizationMemberJoined(id, invitation.getMember().getId()));
    }

    /**
     * @throws OrganizationOwnerNotValidException see {@link #ensureOrganizationOwnerIsValid(OrganizationOwner)}
     */
    public void addProject(OrganizationOwner owner, Project project) {
        ensureOrganizationOwnerIsValid(owner);

        raiseEvent(EventFactory.organizationProjectCreated(id, project.id(), project.name()));
    }

    /**
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
