package org.example.issuestracker.users.command.ui.http.rest.v1.dto;

public record RegisterUserDto(String userEmail, String userPlainPassword) {
    public static final String USER_EMAIL_FIELD_NAME = "userEmail";
    public static final String USER_PLAIN_PASSWORD_FIELD_NAME = "userPlainPassword";
}
