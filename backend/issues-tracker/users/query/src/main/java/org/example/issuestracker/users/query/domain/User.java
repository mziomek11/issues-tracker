package org.example.issuestracker.users.query.domain;

import lombok.Data;
import org.example.issuestracker.shared.domain.event.UserRegisteredEvent;
import org.example.issuestracker.shared.domain.valueobject.UserStatus;

import java.util.UUID;

@Data
public class User {
    private UUID id;
    private String email;
    private String password;
    private UserStatus status;

    public static User register(UserRegisteredEvent userRegisteredEvent) {
        var user = new User();

        user.id = UUID.fromString(userRegisteredEvent.getId());
        user.email = userRegisteredEvent.getUserEmail();
        user.password = userRegisteredEvent.getUserHashedPassword();
        user.status = UserStatus.UNVERIFIED;

        return user;
    }

    public void activate() {
        status = UserStatus.ACTIVATED;
    }
}
