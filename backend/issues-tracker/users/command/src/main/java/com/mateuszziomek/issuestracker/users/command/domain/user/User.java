package com.mateuszziomek.issuestracker.users.command.domain.user;

import lombok.NoArgsConstructor;
import com.mateuszziomek.cqrs.domain.AggregateRoot;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserStatus;

import static com.mateuszziomek.issuestracker.users.command.domain.EventFactory.*;

@NoArgsConstructor
public class User extends AggregateRoot {
    private UserId id;
    private UserEmail email;
    private UserHashedPassword password;
    private UserStatus status;

    public static User register(
            UserId userId,
            UserEmail email,
            UserPlainPassword password,
            UserPasswordHashingAlgorithm passwordHashingAlgorithm
    ) {
        var user = new User();
        var hashedPassword = passwordHashingAlgorithm.hash(password);

        user.raiseEvent(userRegistered(userId, email, hashedPassword));

        return user;
    }

    @Override
    public UserId getId() {
        return id;
    }

    public void on(UserRegisteredEvent userRegisteredEvent) {
        id = new UserId(userRegisteredEvent.getId());
        email = new UserEmail(userRegisteredEvent.getUserEmail());
        password = new UserHashedPassword(userRegisteredEvent.getUserHashedPassword());
        status = UserStatus.ACTIVATED;
    }
}
