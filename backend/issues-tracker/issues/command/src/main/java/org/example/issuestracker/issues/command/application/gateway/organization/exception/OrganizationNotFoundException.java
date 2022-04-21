package org.example.issuestracker.issues.command.application.gateway.organization.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;

@RequiredArgsConstructor
@Getter
public class OrganizationNotFoundException extends IllegalStateException {
    private final transient OrganizationId organizationId;
}
