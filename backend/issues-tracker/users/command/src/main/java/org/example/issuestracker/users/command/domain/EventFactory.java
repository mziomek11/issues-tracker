package org.example.issuestracker.users.command.domain;

import org.example.issuestracker.users.command.domain.account.UserActivationToken;
import org.example.issuestracker.users.command.domain.account.UserEmail;
import org.example.issuestracker.users.command.domain.account.UserHashedPassword;
import org.example.issuestracker.users.command.domain.account.UserId;
import org.example.issuestracker.shared.domain.event.UserActivatedEvent;
import org.example.issuestracker.shared.domain.event.UserRegisteredEvent;

public class EventFactory {
    private EventFactory() {}

    public static UserRegisteredEvent userRegistered(
            UserId id,
            UserEmail email,
            UserHashedPassword password,
            UserActivationToken activationToken
    ) {
        return UserRegisteredEvent
                .builder()
                .userId(id.getValue())
                .userEmail(email.text())
                .userHashedPassword(password.text())
                .userActivationToken(activationToken.value())
                .build();
    }

    public static UserActivatedEvent userActivated(UserId id) {
        return UserActivatedEvent
                .builder()
                .userId(id.getValue())
                .build();
    }
}
