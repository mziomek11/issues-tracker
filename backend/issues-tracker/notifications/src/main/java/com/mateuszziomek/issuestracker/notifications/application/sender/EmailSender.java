package com.mateuszziomek.issuestracker.notifications.application.sender;

import com.mateuszziomek.issuestracker.notifications.domain.email.EmailMessage;

public interface EmailSender {
    void send(EmailMessage message);
}
