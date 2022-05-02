package com.mateuszziomek.issuestracker.notifications.domain.email;

import java.util.Objects;

public record EmailMessageRecipient(String email) {
    public EmailMessageRecipient {
        Objects.requireNonNull(email);
    }
}
