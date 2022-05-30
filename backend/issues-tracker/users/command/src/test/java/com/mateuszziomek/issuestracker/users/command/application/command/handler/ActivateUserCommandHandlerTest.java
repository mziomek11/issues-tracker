package com.mateuszziomek.issuestracker.users.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.mateuszziomek.issuestracker.users.command.application.command.handler.helpers.UserCommandData.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ActivateUserCommandHandlerTest {
    @Test
    void eventSourcingHandlerIsNotCalledWhenUserDoesNotExist() {
        // Arrange
        var eventSourcingHandler = mock(EventSourcingHandler.class);
        when(eventSourcingHandler.getById(any())).thenReturn(Optional.empty());
        var sut = new ActivateUserCommandHandler(eventSourcingHandler);

        // Assert
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> sut.handle(ACTIVATE_USER_COMMAND));
        verify(eventSourcingHandler, times(0)).save(any());
    }
}
