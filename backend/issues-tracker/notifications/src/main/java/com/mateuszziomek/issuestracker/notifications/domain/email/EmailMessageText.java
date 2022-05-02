package com.mateuszziomek.issuestracker.notifications.domain.email;

import java.util.Objects;

public record EmailMessageText(String text) {
    public EmailMessageText {
        Objects.requireNonNull(text);
    }
}
