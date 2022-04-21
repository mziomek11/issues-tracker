package org.example.issuestracker.organizations.command.ui.http.rest.v1.dto;

public record InviteOrganizationMemberDto(String email) {
    public static final String MEMBER_EMAIL_FIELD_NAME = "email";
}
