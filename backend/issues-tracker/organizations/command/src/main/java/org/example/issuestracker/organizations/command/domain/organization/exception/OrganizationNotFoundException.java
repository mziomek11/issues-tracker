package org.example.issuestracker.organizations.command.domain.organization.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.organizations.command.domain.organization.OrganizationId;

@RequiredArgsConstructor
@Getter
public class OrganizationNotFoundException extends IllegalStateException {
    private final transient OrganizationId organizationId;
}
