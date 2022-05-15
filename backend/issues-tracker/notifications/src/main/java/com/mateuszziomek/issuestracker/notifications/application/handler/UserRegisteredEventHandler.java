package com.mateuszziomek.issuestracker.notifications.application.handler;

import com.mateuszziomek.cqrs.event.EventHandler;
import com.mateuszziomek.issuestracker.notifications.application.sender.EmailSender;
import com.mateuszziomek.issuestracker.notifications.domain.email.EmailMessage;
import com.mateuszziomek.issuestracker.notifications.domain.email.EmailMessageRecipient;
import com.mateuszziomek.issuestracker.notifications.domain.email.EmailMessageText;
import com.mateuszziomek.issuestracker.notifications.domain.email.EmailMessageSubject;
import com.mateuszziomek.issuestracker.notifications.domain.user.User;
import com.mateuszziomek.issuestracker.notifications.domain.user.UserRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventHandler implements EventHandler<UserRegisteredEvent> {
    private final UserRepository userRepository;
    private final EmailSender emailSender;

    @Override
    public void handle(UserRegisteredEvent event) {
        if (userRepository.findById(event.getId()).isPresent()) {
            return;
        }

        var user = User.register(event);
        var message = new EmailMessage(
                new EmailMessageSubject("Activate account"),
                new EmailMessageRecipient(event.getUserEmail()),
                new EmailMessageText(event.getId() + " | " + event.getUserActivationToken())
        );

        userRepository.save(user);
        emailSender.send(message);
    }
}
