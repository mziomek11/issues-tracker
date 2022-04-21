package org.example.issuestracker.organizations.command.ui.http.rest.v1.dto;

public record CreateOrganizationProjectDto(String name) {
    public static final String PROJECT_NAME_FIELD_NAME = "name";
}
