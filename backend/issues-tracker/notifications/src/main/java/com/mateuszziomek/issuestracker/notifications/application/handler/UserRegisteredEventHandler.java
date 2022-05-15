package com.mateuszziomek.issuestracker.notifications.application.handler;

import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
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
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventHandler implements ReactiveEventHandler<UserRegisteredEvent> {
    private final UserRepository userRepository;
    private final EmailSender emailSender;

    @Override
    public Mono<Void> handle(UserRegisteredEvent event) {
        return userRepository
                .findById(event.getId())
                .switchIfEmpty(Mono.defer(() -> userRepository.save(User.register(event))))
                .flatMap(user -> emailSender.send(createMessage(event)));
    }

    private EmailMessage createMessage(UserRegisteredEvent event) {
        return new EmailMessage(
                new EmailMessageSubject("Activate account"),
                new EmailMessageRecipient(event.getUserEmail()),
                new EmailMessageText(event.getId() + " | " + event.getUserActivationToken())
        );
    }
}
