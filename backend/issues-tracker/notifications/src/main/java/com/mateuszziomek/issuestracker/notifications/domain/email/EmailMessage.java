package com.mateuszziomek.issuestracker.notifications.domain.email;

import java.util.Objects;

public record EmailMessage(EmailMessageSubject subject, EmailMessageRecipient recipient, EmailMessageText text) {
    public EmailMessage {
        Objects.requireNonNull(subject);
        Objects.requireNonNull(recipient);
        Objects.requireNonNull(text);
    }
}
