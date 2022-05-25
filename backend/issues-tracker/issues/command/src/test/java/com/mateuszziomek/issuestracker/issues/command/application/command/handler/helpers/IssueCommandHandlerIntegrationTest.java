package com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers;

import static com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandData.*;
import static org.mockito.Mockito.*;

import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.ReactiveOrganizationRestClient;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public abstract class IssueCommandHandlerIntegrationTest {
    protected ReactiveOrganizationRestClient createOrganizationRestClientMock() {
        var organizationRestClient = mock(ReactiveOrganizationRestClient.class);


        when(organizationRestClient.getOrganizationById(ORGANIZATION_UUID))
                .thenReturn(Mono.just(createDetailsOrganization()));

        return organizationRestClient;
    }

    private DetailsOrganization createDetailsOrganization() {
        var member = DetailsOrganization.Member
                .builder()
                .id(MEMBER_UUID)
                .build();

        var project = DetailsOrganization.Project
                .builder()
                .id(PROJECT_UUID)
                .name("Example name")
                .build();

        return DetailsOrganization
                .builder()
                .id(ORGANIZATION_UUID)
                .members(List.of(member))
                .projects(List.of(project))
                .ownerId(UUID.randomUUID())
                .build();
    }
}
