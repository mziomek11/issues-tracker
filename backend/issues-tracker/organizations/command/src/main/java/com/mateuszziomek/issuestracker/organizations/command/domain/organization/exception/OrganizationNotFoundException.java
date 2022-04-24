package com.mateuszziomek.issuestracker.organizations.command.domain.organization.exception;

import com.mateuszziomek.issuestracker.organizations.command.domain.organization.OrganizationId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrganizationNotFoundException extends IllegalStateException {
    private final transient OrganizationId organizationId;
}
