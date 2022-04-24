package com.mateuszziomek.issuestracker.users.command.domain;

import com.mateuszziomek.issuestracker.users.command.domain.user.UserActivationToken;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserHashedPassword;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserId;
import com.mateuszziomek.issuestracker.shared.domain.event.UserActivatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;

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
