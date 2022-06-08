package com.mateuszziomek.issuestracker.organizations.command.projection;

import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Member {
    private UUID id;
    private String email;

    public static Member register(UserRegisteredEvent userRegisteredEvent) {
        var user = new Member();

        user.id = userRegisteredEvent.getId();
        user.email = userRegisteredEvent.getUserEmail();

        return user;
    }
}
