package com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception;

import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrganizationMemberNotFoundException extends IllegalStateException {
    private final transient OrganizationMemberId organizationMemberId;
}
