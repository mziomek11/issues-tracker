package com.mateuszziomek.issuestracker.users.command.application.command.handler;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import com.mateuszziomek.issuestracker.users.command.application.command.handler.helpers.UserCommandHandlerIntegrationTest;
import com.mateuszziomek.issuestracker.users.command.application.service.user.UserService;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserHashedPassword;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserPasswordHashingAlgorithm;
import com.mateuszziomek.issuestracker.users.command.domain.user.UserPlainPassword;
import com.mateuszziomek.issuestracker.users.command.projection.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.mateuszziomek.issuestracker.users.command.application.command.handler.helpers.UserCommandData.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RegisterUserCommandHandlerIntegrationTest extends UserCommandHandlerIntegrationTest {
    @Test
    void registeringUserSavesEventToDbAndSendsMessageToBroker() {
        // Arrange
        var eventProducer = mock(EventProducer.class);
        var eventStoreRepository = mock(EventStoreRepository.class);
        var userRepository = mock(UserRepository.class);
        var sut = createHandler(eventProducer, eventStoreRepository, userRepository);

        when(userRepository.findByEmail(argThat(p -> p.equals(USER_EMAIL_PLAIN))))
                .thenReturn(Optional.empty());

        // Act
        sut.handle(REGISTER_USER_COMMAND);

        // Assert
        verify(eventProducer, times(1)).produce(
                eq("UserRegisteredEvent"),
                argThat(event -> hasUserRegisteredEventCorrectedData((UserRegisteredEvent) event))
        );

        verify(eventStoreRepository, times(1)).save(
                argThat(eventModel -> (
                        eventModel.eventType().equals("UserRegisteredEvent")
                        && eventModel.aggregateId().getValue().equals(USER_UUID))
                        && hasUserRegisteredEventCorrectedData((UserRegisteredEvent) eventModel.eventData())
                )
        );
    }

    private RegisterUserCommandHandler createHandler(
            EventProducer eventProducer,
            EventStoreRepository eventStoreRepository,
            UserRepository userRepository
    ) {
        var eventStore = createEventStore(eventStoreRepository, eventProducer);
        var eventSourcingHandler = createSourcingHandler(eventStore);
        var userService = new UserService(userRepository);

        return new RegisterUserCommandHandler(eventSourcingHandler, userService, new TestPasswordHashingAlgorithm());
    }

    private boolean hasUserRegisteredEventCorrectedData(UserRegisteredEvent event) {
        var command = REGISTER_USER_COMMAND;

        return (
                event.getId().equals(command.getUserId().getValue())
                && event.getUserEmail().equals(command.getUserEmail().text())
                && event.getUserHashedPassword().equals("__hashed__" + command.getUserPlainPassword().text())
        );
    }

    private static class TestPasswordHashingAlgorithm implements UserPasswordHashingAlgorithm {
        @Override
        public UserHashedPassword hash(UserPlainPassword password) {
            return new UserHashedPassword("__hashed__" + password.text());
        }
    }
}
