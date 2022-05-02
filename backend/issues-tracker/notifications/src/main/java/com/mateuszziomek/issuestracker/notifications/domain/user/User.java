package com.mateuszziomek.issuestracker.notifications.domain.user;

import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
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