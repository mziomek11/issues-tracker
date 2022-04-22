package org.example.issuestracker.users.command.domain.user;

import lombok.NoArgsConstructor;
import org.example.cqrs.domain.AggregateRoot;
import org.example.issuestracker.users.command.domain.user.exception.UserActivationTokenMismatchException;
import org.example.issuestracker.users.command.domain.user.exception.UserAlreadyActivatedException;
import org.example.issuestracker.shared.domain.event.UserActivatedEvent;
import org.example.issuestracker.shared.domain.event.UserRegisteredEvent;
import org.example.issuestracker.shared.domain.valueobject.UserStatus;

import java.util.Optional;

import static org.example.issuestracker.users.command.domain.EventFactory.*;

@NoArgsConstructor
public class User extends AggregateRoot {
    private UserId id;
    private UserEmail email;
    private UserHashedPassword password;
    private UserStatus status;
    private Optional<UserActivationToken> activationToken;

    public static User register(
            UserId userId,
            UserEmail email,
            UserPlainPassword password,
            UserPasswordHashingAlgorithm passwordHashingAlgorithm
    ) {
        var user = new User();
        var hashedPassword = passwordHashingAlgorithm.hash(password);

        user.raiseEvent(userRegistered(userId, email, hashedPassword, UserActivationToken.generate()));

        return user;
    }

    /**
     * @throws UserActivationTokenMismatchException if user can't be activated with given token
     * @throws UserAlreadyActivatedException if can is already activated
     */
    public void activate(UserActivationToken token) {
        if (isActivated()) {
            throw new UserAlreadyActivatedException(id);
        }

        if (activationToken.isEmpty() || !activationToken.get().equals(token)) {
            throw new UserActivationTokenMismatchException(id, token);
        }

        raiseEvent(userActivated(id));
    }

    private boolean isActivated() {
        return this.status.equals(UserStatus.ACTIVATED);
    }

    @Override
    public UserId getId() {
        return id;
    }

    public void on(UserRegisteredEvent userRegisteredEvent) {
        id = new UserId(userRegisteredEvent.getId());
        email = new UserEmail(userRegisteredEvent.getUserEmail());
        password = new UserHashedPassword(userRegisteredEvent.getUserHashedPassword());
        status = UserStatus.UNVERIFIED;
        activationToken = Optional.of(UserActivationToken.fromString(userRegisteredEvent.getUserActivationToken()));
    }

    public void on(UserActivatedEvent userActivatedEvent) {
        status = UserStatus.ACTIVATED;
        activationToken = Optional.empty();
    }
}
