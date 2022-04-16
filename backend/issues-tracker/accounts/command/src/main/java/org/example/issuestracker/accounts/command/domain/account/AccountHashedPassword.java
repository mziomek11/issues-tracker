package org.example.issuestracker.accounts.command.domain.account;

import java.util.Objects;

public record AccountHashedPassword(String text) {
    public AccountHashedPassword {
        Objects.requireNonNull(text);
    }
}
