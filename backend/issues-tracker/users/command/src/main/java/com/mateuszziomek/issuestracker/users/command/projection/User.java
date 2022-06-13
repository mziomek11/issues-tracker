package com.mateuszziomek.issuestracker.users.command.projection;

import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import lombok.Getter;

import java.util.UUID;

@Getter
public class User {
    private UUID id;
    private String email;

    public static User register(UserRegisteredEvent userRegisteredEvent) {
        var user = new User();

        user.id = userRegisteredEvent.getId();
        user.email = userRegisteredEvent.getUserEmail();

        return user;
    }
}
