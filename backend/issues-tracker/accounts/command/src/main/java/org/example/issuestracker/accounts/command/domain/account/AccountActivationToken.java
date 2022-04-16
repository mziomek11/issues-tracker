package org.example.issuestracker.accounts.command.domain.account;

import java.util.Objects;
import java.util.UUID;

public record AccountActivationToken(UUID value) {
    public static AccountActivationToken generate() {
        return new AccountActivationToken(UUID.randomUUID());
    }

    public static AccountActivationToken fromString(String value) {
        return new AccountActivationToken(UUID.fromString(value));
    }

    public AccountActivationToken {
        Objects.requireNonNull(value);
    }
}
