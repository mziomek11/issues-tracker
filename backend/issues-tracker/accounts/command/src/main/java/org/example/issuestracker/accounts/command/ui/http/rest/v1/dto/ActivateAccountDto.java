package org.example.issuestracker.accounts.command.ui.http.rest.v1.dto;

import java.util.UUID;

public record ActivateAccountDto(UUID accountActivationToken) {
    public static final String ACCOUNT_ACTIVATION_TOKEN_FIELD_NAME = "accountActivationToken";
}
