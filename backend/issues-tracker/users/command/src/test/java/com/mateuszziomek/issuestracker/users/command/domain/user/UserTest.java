package com.mateuszziomek.issuestracker.users.command.domain.user;

import com.mateuszziomek.cqrs.domain.AbstractAggregateRootTest;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
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
