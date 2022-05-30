package com.mateuszziomek.issuestracker.organizations.command.domain.organization;

import com.mateuszziomek.cqrs.domain.AbstractAggregateRootTest;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.Invitation;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception.InvitationAlreadyPresentException;
import com.mateuszziomek.issuestracker.organizations.command.domain.invitation.exception.InvitationNotFoundException;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.Member;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.MemberId;
import com.mateuszziomek.issuestracker.organizations.command.domain.member.exception.MemberAlreadyPresentException;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception.OrganizationOwnerNotValidException;
import com.mateuszziomek.issuestracker.organizations.command.domain.project.Project;
import com.mateuszziomek.issuestracker.organizations.command.domain.project.ProjectId;
import com.mateuszziomek.issuestracker.organizations.command.domain.project.ProjectName;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class OrganizationTest extends AbstractAggregateRootTest {
    private final UUID ORGANIZATION_UUID = UUID.randomUUID();
    private final UUID ORGANIZATION_OWNER_UUID = UUID.randomUUID();
    private final UUID INVITED_MEMBER_UUID = UUID.randomUUID();
    private final OrganizationOwner ORGANIZATION_OWNER = new OrganizationOwner(new MemberId(ORGANIZATION_OWNER_UUID));

    @Test
    void creatingOrganizationRaisesOrganizationCreatedEvent() {
        // Arrange
        var organizationUUID = UUID.randomUUID();
        var organizationId = new OrganizationId(organizationUUID);
        var ownerUUID = UUID.randomUUID();
        var organizationOwner = new OrganizationOwner(new MemberId(ownerUUID));
        var organizationName = new OrganizationName("Example organization");

        // Act
        var sut = Organization.create(organizationId, organizationOwner, organizationName);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, OrganizationCreatedEvent.class);

        var organizationCreatedEvent = (OrganizationCreatedEvent) sut.getUncommittedChanges().get(0);
        assertThat(organizationCreatedEvent.getId()).isEqualTo(organizationUUID);
        assertThat(organizationCreatedEvent.getOrganizationOwnerId()).isEqualTo(ownerUUID);
        assertThat(organizationCreatedEvent.getOrganizationName()).isEqualTo("Example organization");
    }

    @Test
    void invitingToOrganizationRaisesOrganizationMemberInvitedEvent() {
        // Arrange
        var sut = createOrganization();
        var invitedMemberUUID = UUID.randomUUID();
        var invitation = new Invitation(new Member(new MemberId(invitedMemberUUID)));

        // Act
        sut.invite(ORGANIZATION_OWNER, invitation);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, OrganizationMemberInvitedEvent.class);

        var organizationMemberInvitedEvent = (OrganizationMemberInvitedEvent) sut.getUncommittedChanges().get(0);
        assertThat(organizationMemberInvitedEvent.getId()).isEqualTo(ORGANIZATION_UUID);
        assertThat(organizationMemberInvitedEvent.getMemberId()).isEqualTo(invitedMemberUUID);
    }

    @Test
    void organizationCanNotHaveTwoTheSameInvitations() {
        // Arrange
        var sut = createOrganization();
        var invitation = createInvitation();
        var anotherInvitation = createInvitation();
        sut.invite(ORGANIZATION_OWNER, invitation);
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(InvitationAlreadyPresentException.class)
                .isThrownBy(() -> sut.invite(ORGANIZATION_OWNER, anotherInvitation));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void existingOrganizationMemberCanNotBeInvited() {
        // Arrange
        var sut = createOrganization();
        var invitation = createInvitation();
        sut.invite(ORGANIZATION_OWNER, invitation);
        sut.acceptInvitation(invitation);
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(MemberAlreadyPresentException.class)
                .isThrownBy(() -> sut.invite(ORGANIZATION_OWNER, invitation));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void invitationCanBeMadeOnlyByOrganizationOwner() {
        // Arrange
        var sut = createOrganization();
        var invitation = createInvitation();
        var organizationOwner = new OrganizationOwner(new MemberId(UUID.randomUUID()));

        // Assert
        assertThatExceptionOfType(OrganizationOwnerNotValidException.class)
                .isThrownBy(() -> sut.invite(organizationOwner, invitation));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void acceptingInvitationRaisesOrganizationMemberJoinedEvent() {
        // Arrange
        var sut = createOrganization();
        var invitation = createInvitation();
        sut.invite(ORGANIZATION_OWNER, invitation);
        sut.markChangesAsCommitted();

        // Act
        sut.acceptInvitation(invitation);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, OrganizationMemberJoinedEvent.class);

        var organizationMemberJoinedEvent = (OrganizationMemberJoinedEvent) sut.getUncommittedChanges().get(0);
        assertThat(organizationMemberJoinedEvent.getId()).isEqualTo(ORGANIZATION_UUID);
        assertThat(organizationMemberJoinedEvent.getMemberId()).isEqualTo(INVITED_MEMBER_UUID);
    }

    @Test
    void notExistingInvitationCanNotBeAccepted() {
        // Arrange
        var sut = createOrganization();
        var invitation = createInvitation();

        // Assert
        assertThatExceptionOfType(InvitationNotFoundException.class)
                .isThrownBy(() -> sut.acceptInvitation(invitation));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void addingProjectRaisesOrganizationProjectCreatedEvent() {
        // Arrange
        var sut = createOrganization();
        var projectUUID = UUID.randomUUID();
        var project = new Project(new ProjectId(projectUUID), new ProjectName("Example name"));

        // Act
        sut.addProject(ORGANIZATION_OWNER, project);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, OrganizationProjectCreatedEvent.class);

        var organizationProjectCreatedEvent = (OrganizationProjectCreatedEvent) sut.getUncommittedChanges().get(0);
        assertThat(organizationProjectCreatedEvent.getId()).isEqualTo(ORGANIZATION_UUID);
        assertThat(organizationProjectCreatedEvent.getProjectId()).isEqualTo(projectUUID);
        assertThat(organizationProjectCreatedEvent.getProjectName()).isEqualTo("Example name");
    }

    @Test
    void projectCanBeCreatedOnlyByOrganizationOwner() {
        // Arrange
        var sut = createOrganization();
        var project = new Project(new ProjectId(UUID.randomUUID()), new ProjectName("Example name"));
        var organizationOwner = new OrganizationOwner(new MemberId(UUID.randomUUID()));

        // Assert
        assertThatExceptionOfType(OrganizationOwnerNotValidException.class)
                .isThrownBy(() -> sut.addProject(organizationOwner, project));
        assertThatNoEventsAreRaised(sut);
    }

    private Organization createOrganization() {
        var organizationId = new OrganizationId(ORGANIZATION_UUID);
        var organizationOwner = new OrganizationOwner(new MemberId(ORGANIZATION_OWNER_UUID));
        var organizationName = new OrganizationName("Example organization");
        var organization = Organization.create(organizationId, organizationOwner, organizationName);
        organization.markChangesAsCommitted();

        return organization;
    }

    private Invitation createInvitation() {
        return new Invitation(new Member(new MemberId(INVITED_MEMBER_UUID)));
    }
}
