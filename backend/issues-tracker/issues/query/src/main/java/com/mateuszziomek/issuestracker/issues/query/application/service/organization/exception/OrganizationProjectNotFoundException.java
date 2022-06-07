package com.mateuszziomek.issuestracker.issues.query.application.service.organization.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class OrganizationProjectNotFoundException extends IllegalStateException {
    private final UUID organizationProjectId;
}
