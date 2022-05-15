package com.mateuszziomek.issuestracker.notifications.application.sender;

import com.mateuszziomek.issuestracker.notifications.domain.email.EmailMessage;
import reactor.core.publisher.Mono;

public interface EmailSender {
    Mono<Void> send(EmailMessage message);
}
