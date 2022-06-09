package com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers;

import static com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandData.*;
import static org.mockito.Mockito.*;

import com.mateuszziomek.issuestracker.issues.command.application.service.organization.OrganizationService;

public abstract class IssueCommandHandlerTest {
    protected OrganizationService createOrganizationGatewayExceptionMock(RuntimeException expectedException) {
        var organizationGateway = mock(OrganizationService.class);

        doThrow(expectedException)
                .when(organizationGateway)
                .ensureOrganizationHasProjectAndMember(ORGANIZATION_DETAILS);

        return organizationGateway;
    }
}
