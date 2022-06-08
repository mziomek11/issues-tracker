package com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception;

import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrganizationNotFoundException extends IllegalStateException {
    private final OrganizationId organizationId;
}
