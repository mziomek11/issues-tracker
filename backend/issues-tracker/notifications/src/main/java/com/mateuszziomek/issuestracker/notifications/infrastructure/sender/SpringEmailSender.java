package com.mateuszziomek.issuestracker.notifications.infrastructure.sender;

import com.mateuszziomek.issuestracker.notifications.application.sender.EmailSender;
import com.mateuszziomek.issuestracker.notifications.domain.email.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEmailSender implements EmailSender {
    private final JavaMailSender emailSender;

    @Override
    public void send(EmailMessage message) {
        var simpleMessage = new SimpleMailMessage();

        simpleMessage.setFrom("noreply@issuestracker.mateuszziomek.com");
        simpleMessage.setTo(message.recipient().email());
        simpleMessage.setSubject(message.subject().text());
        simpleMessage.setText(message.text().text());

        emailSender.send(simpleMessage);
    }
}
