package com.mateuszziomek.issuestracker.users.command.domain.user;

import com.mateuszziomek.cqrs.domain.AbstractAggregateRootTest;
import com.mateuszziomek.issuestracker.shared.domain.event.UserActivatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserActivationTokenMismatchException;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserAlreadyActivatedException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class UserTest extends AbstractAggregateRootTest {
    private final UUID USER_UUID = UUID.randomUUID();

    @Test
    void registeringUserRaisesUserRegisteredEvent() {
        // Arrange
        var userUUID = UUID.randomUUID();
        var userId = new UserId(userUUID);
        var userEmail = new UserEmail("example@mail.com");
        var userPassword = new UserPlainPassword("qwerty99");

        // Act
        var sut = User.register(userId, userEmail, userPassword, new TestPasswordHashingAlgorithm());

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, UserRegisteredEvent.class);

        var userRegisteredEvent = (UserRegisteredEvent) sut.getUncommittedChanges().get(0);
        assertThat(userRegisteredEvent.getId()).isEqualTo(userUUID);
        assertThat(userRegisteredEvent.getUserEmail()).isEqualTo("example@mail.com");
        assertThat(userRegisteredEvent.getUserHashedPassword()).isEqualTo("__hashed__qwerty99");
        assertThat(userRegisteredEvent.getUserActivationToken()).isNotNull();
    }

    @Test
    void activatingUserRaisesUserActivatedEvent() {
        // Arrange
        var sut = registerUser(false);
        var userRegisteredEvent = (UserRegisteredEvent) sut.getUncommittedChanges().get(0);
        var activationToken = new UserActivationToken(userRegisteredEvent.getUserActivationToken());
        sut.markChangesAsCommitted();

        // Act
        sut.activate(activationToken);

        // Assert
        assertThatTheOnlyRaisedEventIs(sut, UserActivatedEvent.class);

        var userActivatedEvent = (UserActivatedEvent) sut.getUncommittedChanges().get(0);
        assertThat(userActivatedEvent.getId()).isEqualTo(USER_UUID);
    }

    @Test
    void userCanNotBeActivatedTwice() {
        // Arrange
        var sut = registerUser(false);
        var userRegisteredEvent = (UserRegisteredEvent) sut.getUncommittedChanges().get(0);
        var activationToken = new UserActivationToken(userRegisteredEvent.getUserActivationToken());
        sut.activate(activationToken);
        sut.markChangesAsCommitted();

        // Assert
        assertThatExceptionOfType(UserAlreadyActivatedException.class)
                .isThrownBy(() -> sut.activate(activationToken));
        assertThatNoEventsAreRaised(sut);
    }

    @Test
    void userCanNotBeActivatedWithInvalidActivationToken() {
        // Arrange
        var sut = registerUser(true);
        var invalidActivationToken = new UserActivationToken(UUID.randomUUID());

        // Assert
        assertThatExceptionOfType(UserActivationTokenMismatchException.class)
                .isThrownBy(() -> sut.activate(invalidActivationToken));
        assertThatNoEventsAreRaised(sut);
    }

    private User registerUser(boolean markChangesAsCommitted) {
        var userId = new UserId(USER_UUID);
        var userEmail = new UserEmail("example@mail.com");
        var userPassword = new UserPlainPassword("qwerty99");

        var user = User.register(userId, userEmail, userPassword, new TestPasswordHashingAlgorithm());

        if (markChangesAsCommitted) {
            user.markChangesAsCommitted();
        }

        return user;
    }

    private static final class TestPasswordHashingAlgorithm implements UserPasswordHashingAlgorithm {
        @Override
        public UserHashedPassword hash(UserPlainPassword password) {
            return new UserHashedPassword("__hashed__" + password.text());
        }
    }
}
