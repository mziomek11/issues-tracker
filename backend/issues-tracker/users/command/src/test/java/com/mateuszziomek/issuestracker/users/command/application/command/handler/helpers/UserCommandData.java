package com.mateuszziomek.issuestracker.users.command.application.command.handler.helpers;

import com.mateuszziomek.cqrs.event.EventModel;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import com.mateuszziomek.issuestracker.users.command.application.command.ActivateUserCommand;
import com.mateuszziomek.issuestracker.users.command.application.command.RegisterUserCommand;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserId;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class UserCommandData {
    public static final UUID USER_UUID = UUID.randomUUID();
    public static final UserId USER_ID = new UserId(USER_UUID);
    public static final UUID USER_ACTIVATION_TOKEN_UUID = UUID.randomUUID();
    public static final String USER_EMAIL_PLAIN ="example@mail.com";

    public static final RegisterUserCommand REGISTER_USER_COMMAND = RegisterUserCommand
            .builder()
            .userId(USER_UUID)
            .userEmail(USER_EMAIL_PLAIN)
            .userPlainPassword("Example password")
            .build();

    public static final ActivateUserCommand ACTIVATE_USER_COMMAND = ActivateUserCommand
            .builder()
            .userId(USER_UUID)
            .userActivationToken(USER_ACTIVATION_TOKEN_UUID)
            .build();

    public static final UserRegisteredEvent USER_REGISTERED_EVENT = UserRegisteredEvent
            .builder()
            .userId(USER_UUID)
            .userEmail(USER_EMAIL_PLAIN)
            .userActivationToken(USER_ACTIVATION_TOKEN_UUID)
            .userHashedPassword("__hashed__Example password")
            .build();

    public static final EventModel USER_REGISTERED_EVENT_MODEL = new EventModel(
            USER_UUID,
            Date.from(Instant.now()),
            USER_ID,
            UserRegisteredEvent.class.getTypeName(),
            0,
            "UserRegisteredEvent",
            USER_REGISTERED_EVENT
    );

    private UserCommandData() {}
}
