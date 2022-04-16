package org.example.issuestracker.accounts.command.domain.account;

import java.util.Objects;

public record AccountPlainPassword(String text) {
    public AccountPlainPassword {
        Objects.requireNonNull(text);
    }
}
