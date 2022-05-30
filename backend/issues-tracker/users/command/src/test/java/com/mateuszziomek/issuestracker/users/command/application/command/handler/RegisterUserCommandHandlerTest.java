package com.mateuszziomek.issuestracker.users.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.UserGateway;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserEmailUnavailableException;
import com.mateuszziomek.issuestracker.users.command.application.gateway.user.exception.UserServiceUnavailableException;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserEmail;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserPasswordHashingAlgorithm;
import com.mateuszziomek.issuestracker.users.command.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static com.mateuszziomek.issuestracker.users.command.application.command.handler.helpers.UserCommandData.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RegisterUserCommandHandlerTest {
    @ParameterizedTest
    @ArgumentsSource(UserGatewayExceptionArgumentProvider.class)
    void eventSourcingHandlerIsNotCalledWhenGatewayThrows(RuntimeException gatewayException) {
        // Arrange
        var eventSourcingHandler = mock(EventSourcingHandler.class);
        var userGateway = mock(UserGateway.class);
        var passwordHashingAlgorithm = mock(UserPasswordHashingAlgorithm.class);
        var sut = new RegisterUserCommandHandler(eventSourcingHandler, userGateway, passwordHashingAlgorithm);

        doThrow(gatewayException)
                .when(userGateway)
                .ensureUserEmailIsAvailable(argThat(email -> email.text().equals(USER_EMAIL_PLAIN)));

        // Assert
        assertThatExceptionOfType(gatewayException.getClass())
                .isThrownBy(() -> sut.handle(REGISTER_USER_COMMAND));
        verify(eventSourcingHandler, times(0)).save(any());
    }

    private static class UserGatewayExceptionArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.of(new UserEmailUnavailableException(new UserEmail(USER_EMAIL_PLAIN))),
                    Arguments.of(new UserServiceUnavailableException())
            );
        }
    }
}
