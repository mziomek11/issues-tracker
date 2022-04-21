package org.example.issuestracker.issues.command.application.gateway.organization.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.issues.command.domain.organization.OrganizationProjectId;

@RequiredArgsConstructor
@Getter
public class OrganizationProjectNotFoundException extends IllegalStateException {
    private final transient OrganizationProjectId organizationProjectId;
}
