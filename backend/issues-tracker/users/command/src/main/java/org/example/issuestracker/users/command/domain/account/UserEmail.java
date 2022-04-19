package org.example.issuestracker.users.command.domain.account;

import java.util.Objects;

public record UserEmail(String text) {
    public UserEmail {
        Objects.requireNonNull(text);
    }
}
