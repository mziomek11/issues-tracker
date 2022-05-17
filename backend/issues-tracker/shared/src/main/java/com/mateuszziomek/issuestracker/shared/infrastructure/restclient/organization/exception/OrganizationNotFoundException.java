package com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.exception;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class OrganizationNotFoundException extends IllegalStateException {
    private final UUID organizationId;
}
