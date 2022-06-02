package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.organization;

public record CreateOrganizationDto(String name) {
    public static final String ORGANIZATION_NAME_FIELD_NAME = "name";
}

