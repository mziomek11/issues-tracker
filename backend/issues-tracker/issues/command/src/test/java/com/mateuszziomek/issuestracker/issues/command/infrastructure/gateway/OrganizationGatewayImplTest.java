package com.mateuszziomek.issuestracker.issues.command.infrastructure.gateway;

import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationServiceUnavailableException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.ReactiveOrganizationRestClient;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrganizationGatewayImplTest {
    private static final UUID ORGANIZATION_UUID = UUID.randomUUID();
    private static final UUID PROJECT_UUID = UUID.randomUUID();
    private static final UUID MEMBER_UUID = UUID.randomUUID();
    private static final IssueOrganizationDetails ORGANIZATION_DETAILS = new IssueOrganizationDetails(
        new OrganizationId(ORGANIZATION_UUID),
        new OrganizationProjectId(PROJECT_UUID),
        new OrganizationMemberId(MEMBER_UUID)
    );

    @Test
    void throwsWhenOrganizationDoesNotExist() {
        // Arrange
        var organizationRestClient = mock(ReactiveOrganizationRestClient.class);
        var ex = new com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.exception.OrganizationNotFoundException(ORGANIZATION_UUID);
        when(organizationRestClient.getOrganizationById(ORGANIZATION_UUID)).thenReturn(Mono.error(ex));
        var sut = new OrganizationGatewayImpl(organizationRestClient);

        // Assert
        assertThatExceptionOfType(OrganizationNotFoundException.class)
                .isThrownBy(() -> sut.ensureOrganizationHasProjectAndMember(ORGANIZATION_DETAILS));
    }

    @Test
    void throwsWhenProjectInNotInOrganization() {
        // Arrange
        var organizationRestClient = mock(ReactiveOrganizationRestClient.class);
        var dto = DetailsOrganization
                .builder()
                .id(ORGANIZATION_UUID)
                .projects(List.of(DetailsOrganization.Project.builder().id(UUID.randomUUID()).build()))
                .members(List.of(DetailsOrganization.Member.builder().id(MEMBER_UUID).build()))
                .build();
        when(organizationRestClient.getOrganizationById(ORGANIZATION_UUID)).thenReturn(Mono.just(dto));
        var sut = new OrganizationGatewayImpl(organizationRestClient);

        // Assert
        assertThatExceptionOfType(OrganizationProjectNotFoundException.class)
                .isThrownBy(() -> sut.ensureOrganizationHasProjectAndMember(ORGANIZATION_DETAILS));
    }

    @Test
    void throwsWhenMemberIsNotInOrganization() {
        // Arrange
        var organizationRestClient = mock(ReactiveOrganizationRestClient.class);
        var dto = DetailsOrganization
                .builder()
                .id(ORGANIZATION_UUID)
                .projects(List.of(DetailsOrganization.Project.builder().id(PROJECT_UUID).build()))
                .members(List.of(DetailsOrganization.Member.builder().id(UUID.randomUUID()).build()))
                .build();
        when(organizationRestClient.getOrganizationById(ORGANIZATION_UUID)).thenReturn(Mono.just(dto));
        var sut = new OrganizationGatewayImpl(organizationRestClient);

        // Assert
        assertThatExceptionOfType(OrganizationMemberNotFoundException.class)
                .isThrownBy(() -> sut.ensureOrganizationHasProjectAndMember(ORGANIZATION_DETAILS));
    }

    @Test
    void throwsWhenOrganizationServiceIsUnavailable() {
        // Arrange
        var organizationRestClient = mock(ReactiveOrganizationRestClient.class);
        var ex = new com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.exception.OrganizationServiceUnavailableException();
        when(organizationRestClient.getOrganizationById(ORGANIZATION_UUID)).thenReturn(Mono.error(ex));
        var sut = new OrganizationGatewayImpl(organizationRestClient);

        // Assert
        assertThatExceptionOfType(OrganizationServiceUnavailableException.class)
                .isThrownBy(() -> sut.ensureOrganizationHasProjectAndMember(ORGANIZATION_DETAILS));
    }

    @Test
    void passesWhenOrganizationProjectAndMemberExist() {
        // Arrange
        var organizationRestClient = mock(ReactiveOrganizationRestClient.class);
        var dto = DetailsOrganization
                .builder()
                .id(ORGANIZATION_UUID)
                .projects(List.of(DetailsOrganization.Project.builder().id(PROJECT_UUID).build()))
                .members(List.of(DetailsOrganization.Member.builder().id(MEMBER_UUID).build()))
                .build();
        when(organizationRestClient.getOrganizationById(ORGANIZATION_UUID)).thenReturn(Mono.just(dto));
        var sut = new OrganizationGatewayImpl(organizationRestClient);

        // Assert
        assertDoesNotThrow(() -> sut.ensureOrganizationHasProjectAndMember(ORGANIZATION_DETAILS));
    }
}
