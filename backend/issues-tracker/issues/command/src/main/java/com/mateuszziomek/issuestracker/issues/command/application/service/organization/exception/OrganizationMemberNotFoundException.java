package com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception;

import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrganizationMemberNotFoundException extends IllegalStateException {
    private final OrganizationMemberId organizationMemberId;
}
