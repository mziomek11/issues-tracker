package org.example.issuestracker.users.command.ui.http.rest.v1.dto;

public record RegisterUserDto(String email, String password) {
    public static final String USER_EMAIL_FIELD_NAME = "email";
    public static final String USER_PLAIN_PASSWORD_FIELD_NAME = "password";
}
