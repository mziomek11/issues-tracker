package com.mateuszziomek.issuestracker.users.command.domain.user;

import lombok.NoArgsConstructor;
import com.mateuszziomek.cqrs.domain.AggregateRoot;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserActivationTokenMismatchException;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserAlreadyActivatedException;
import com.mateuszziomek.issuestracker.shared.domain.event.UserActivatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.UserStatus;

import java.util.Optional;

import static com.mateuszziomek.issuestracker.users.command.domain.EventFactory.*;

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
        var activationToken = UserActivationToken.generate();

        user.raiseEvent(userRegistered(userId, email, hashedPassword, activationToken));

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
        activationToken = Optional.of(new UserActivationToken(userRegisteredEvent.getUserActivationToken()));
    }

    public void on(UserActivatedEvent userActivatedEvent) {
        status = UserStatus.ACTIVATED;
        activationToken = Optional.empty();
    }
}
