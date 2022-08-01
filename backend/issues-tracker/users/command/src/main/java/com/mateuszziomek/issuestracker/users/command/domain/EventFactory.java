package com.mateuszziomek.issuestracker.users.command.domain;

import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserHashedPassword;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserId;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;

public class EventFactory {
    private EventFactory() {}

    public static UserRegisteredEvent userRegistered(
            UserId id,
            UserEmail email,
            UserHashedPassword password
    ) {
        return UserRegisteredEvent
                .builder()
                .userId(id.getValue())
                .userEmail(email.text())
                .userHashedPassword(password.text())
                .build();
    }
}
