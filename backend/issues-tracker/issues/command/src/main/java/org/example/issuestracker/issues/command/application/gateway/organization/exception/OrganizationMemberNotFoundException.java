package org.example.issuestracker.issues.command.application.gateway.organization.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.issues.command.domain.organization.OrganizationMemberId;

@RequiredArgsConstructor
@Getter
public class OrganizationMemberNotFoundException extends IllegalStateException {
    private final transient OrganizationMemberId organizationMemberId;
}
