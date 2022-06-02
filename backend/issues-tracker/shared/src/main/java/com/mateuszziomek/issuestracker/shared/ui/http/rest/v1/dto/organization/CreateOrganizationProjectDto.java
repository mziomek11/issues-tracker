package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.organization;

public record CreateOrganizationProjectDto(String name) {
    public static final String PROJECT_NAME_FIELD_NAME = "name";
}
