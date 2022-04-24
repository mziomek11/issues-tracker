package com.mateuszziomek.issuestracker.organizations.query.application.query.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class OrganizationNotFoundException extends RuntimeException {
    private final UUID organizationId;
}
