package com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers;

import static com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandData.*;
import static org.mockito.Mockito.*;

import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.OrganizationGateway;

public abstract class IssueCommandHandlerTest {
    protected OrganizationGateway createOrganizationGatewayExceptionMock(RuntimeException expectedException) {
        var organizationGateway = mock(OrganizationGateway.class);

        doThrow(expectedException)
                .when(organizationGateway)
                .ensureOrganizationHasProjectAndMember(ORGANIZATION_DETAILS);

        return organizationGateway;
    }
}
