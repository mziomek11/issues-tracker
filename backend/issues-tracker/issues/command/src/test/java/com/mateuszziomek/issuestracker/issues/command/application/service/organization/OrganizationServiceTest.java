package com.mateuszziomek.issuestracker.issues.command.application.service.organization;

import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import com.mateuszziomek.issuestracker.issues.command.projection.Organization;
import com.mateuszziomek.issuestracker.issues.command.projection.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrganizationServiceTest {
    private static final UUID ORGANIZATION_UUID = UUID.randomUUID();
    private static final UUID PROJECT_UUID = UUID.randomUUID();
    private static final UUID MEMBER_UUID = UUID.randomUUID();
    private static final IssueOrganizationDetails ISSUE_ORGANIZATION_DETAILS = new IssueOrganizationDetails(
            new OrganizationId(ORGANIZATION_UUID),
            new OrganizationProjectId(PROJECT_UUID),
            new OrganizationMemberId(MEMBER_UUID)
    );

    @Test
    void throwsWhenMemberWithGivenIdDoesNotExistInOrganization() {
        // Arrange
        var organizationRepository = mock(OrganizationRepository.class);
        var sut = new OrganizationService(organizationRepository);
        var organization = createOrganization();
        organization.addProject(projectCreatedEvent());

        when(organizationRepository.findById(argThat(uuid -> uuid.equals(ORGANIZATION_UUID))))
                .thenReturn(Optional.of(organization));

        // Assert
        assertThatExceptionOfType(OrganizationMemberNotFoundException.class)
                .isThrownBy(() -> sut.ensureOrganizationHasProjectAndMember(ISSUE_ORGANIZATION_DETAILS));
        verify(organizationRepository, times(1))
                .findById(argThat(uuid -> uuid.equals(ORGANIZATION_UUID)));
    }

    @Test
    void throwsWhenWhenOrganizationWithGivenIdDoesNotExist() {
        // Arrange
        var organizationRepository = mock(OrganizationRepository.class);
        var sut = new OrganizationService(organizationRepository);

        when(organizationRepository.findById(argThat(uuid -> uuid.equals(ORGANIZATION_UUID))))
                .thenReturn(Optional.empty());

        // Assert
        assertThatExceptionOfType(OrganizationNotFoundException.class)
                .isThrownBy(() -> sut.ensureOrganizationHasProjectAndMember(ISSUE_ORGANIZATION_DETAILS));
        verify(organizationRepository, times(1))
                .findById(argThat(uuid -> uuid.equals(ORGANIZATION_UUID)));
    }

    @Test
    void throwsWhenProjectWithGivenIdDoesNotExistInOrganization() {
        // Arrange
        var organizationRepository = mock(OrganizationRepository.class);
        var sut = new OrganizationService(organizationRepository);
        var organization = createOrganization();
        organization.joinMember(memberJoinedEvent());

        when(organizationRepository.findById(argThat(uuid -> uuid.equals(ORGANIZATION_UUID))))
                .thenReturn(Optional.of(organization));

        // Assert
        assertThatExceptionOfType(OrganizationProjectNotFoundException.class)
                .isThrownBy(() -> sut.ensureOrganizationHasProjectAndMember(ISSUE_ORGANIZATION_DETAILS));
        verify(organizationRepository, times(1))
                .findById(argThat(uuid -> uuid.equals(ORGANIZATION_UUID)));
    }

    @Test
    void doesNothingWhenOrganizationExistsAndHasProjectAndMemberWithGivenIds() {
        // Arrange
        var organizationRepository = mock(OrganizationRepository.class);
        var sut = new OrganizationService(organizationRepository);
        var organization = createOrganization();
        organization.joinMember(memberJoinedEvent());
        organization.addProject(projectCreatedEvent());

        when(organizationRepository.findById(argThat(uuid -> uuid.equals(ORGANIZATION_UUID))))
                .thenReturn(Optional.of(organization));

        // Act
        sut.ensureOrganizationHasProjectAndMember(ISSUE_ORGANIZATION_DETAILS);
    }

    private Organization createOrganization() {
        return Organization.create(
                OrganizationCreatedEvent
                        .builder()
                        .organizationId(ORGANIZATION_UUID)
                        .organizationName("Example name")
                        .organizationOwnerId(UUID.randomUUID())
                        .build()
        );
    }

    private OrganizationProjectCreatedEvent projectCreatedEvent() {
        return OrganizationProjectCreatedEvent
                .builder()
                .organizationId(ORGANIZATION_UUID)
                .projectId(PROJECT_UUID)
                .projectName("Example name")
                .build();
    }

    private OrganizationMemberJoinedEvent memberJoinedEvent() {
        return OrganizationMemberJoinedEvent
                .builder()
                .organizationId(ORGANIZATION_UUID)
                .memberId(MEMBER_UUID)
                .build();
    }
}
