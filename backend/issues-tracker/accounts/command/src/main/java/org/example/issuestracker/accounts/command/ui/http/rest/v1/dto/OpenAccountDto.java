package org.example.issuestracker.accounts.command.ui.http.rest.v1.dto;

public record OpenAccountDto(String accountEmail, String accountPlainPassword) {
    public static final String ACCOUNT_EMAIL_FIELD_NAME = "accountEmail";
    public static final String ACCOUNT_PLAIN_PASSWORD_FIELD_NAME = "accountPlainPassword";
}
