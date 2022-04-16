package org.example.issuestracker.accounts.command.domain.account;

import java.util.Objects;

public record AccountEmail(String text) {
    public AccountEmail {
        Objects.requireNonNull(text);
    }
}
