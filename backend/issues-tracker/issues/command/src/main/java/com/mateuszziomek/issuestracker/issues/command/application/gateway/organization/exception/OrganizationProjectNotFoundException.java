package com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception;

import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrganizationProjectNotFoundException extends IllegalStateException {
    private final transient OrganizationProjectId organizationProjectId;
}
