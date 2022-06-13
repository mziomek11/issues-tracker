package com.mateuszziomek.issuestracker.users.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.users.command.application.service.user.UserService;
import com.mateuszziomek.issuestracker.users.command.application.service.user.exception.UserEmailUnavailableException;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserPasswordHashingAlgorithm;
import org.junit.jupiter.api.Test;

import static com.mateuszziomek.issuestracker.users.command.application.command.handler.helpers.UserCommandData.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RegisterUserCommandHandlerTest {
    @Test
    void eventSourcingHandlerIsNotCalledWhenUserEmailIsUnavailable() {
        // Arrange
        var eventSourcingHandler = mock(EventSourcingHandler.class);
        var userService = mock(UserService.class);
        var passwordHashingAlgorithm = mock(UserPasswordHashingAlgorithm.class);
        var sut = new RegisterUserCommandHandler(eventSourcingHandler, userService, passwordHashingAlgorithm);

        doThrow(new UserEmailUnavailableException(new UserEmail("example@email.com")))
                .when(userService)
                .ensureUserEmailIsAvailable(argThat(email -> email.text().equals(USER_EMAIL_PLAIN)));

        // Assert
        assertThatExceptionOfType(UserEmailUnavailableException.class)
                .isThrownBy(() -> sut.handle(REGISTER_USER_COMMAND));
        verify(eventSourcingHandler, times(0)).save(any());
    }
}
