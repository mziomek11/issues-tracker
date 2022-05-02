package com.mateuszziomek.issuestracker.notifications.domain.email;

import java.util.Objects;

public record EmailMessageSubject(String text) {
    public EmailMessageSubject {
        Objects.requireNonNull(text);
    }
}
