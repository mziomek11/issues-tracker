package com.mateuszziomek.issuestracker.shared.ui.http.rest.v1.dto.organization;

public record InviteOrganizationMemberDto(String email) {
    public static final String MEMBER_EMAIL_FIELD_NAME = "email";
}
