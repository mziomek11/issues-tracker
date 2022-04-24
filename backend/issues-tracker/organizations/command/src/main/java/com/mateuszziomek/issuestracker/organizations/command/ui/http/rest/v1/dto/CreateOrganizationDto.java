package com.mateuszziomek.issuestracker.organizations.command.ui.http.rest.v1.dto;

public record CreateOrganizationDto(String name) {
    public static final String ORGANIZATION_NAME_FIELD_NAME = "name";
}

