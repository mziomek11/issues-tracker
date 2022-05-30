package com.mateuszziomek.issuestracker.users.command.application.command.handler;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.UserActivatedEvent;
import com.mateuszziomek.issuestracker.users.command.application.command.handler.helpers.UserCommandHandlerIntegrationTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.mateuszziomek.issuestracker.users.command.application.command.handler.helpers.UserCommandData.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ActivateUserCommandHandlerIntegrationTest extends UserCommandHandlerIntegrationTest {
    @Test
    void activatingUserSavesEventToDbAndSendsMessageToBroker() {
        // Arrange
        var eventProducer = mock(EventProducer.class);
        var eventStoreRepository = mock(EventStoreRepository.class);
        var sut = createHandler(eventProducer, eventStoreRepository);

        when(eventStoreRepository.findByAggregateId(USER_ID))
                .thenReturn(List.of(USER_REGISTERED_EVENT_MODEL));

        // Act
        sut.handle(ACTIVATE_USER_COMMAND);

        // Assert
        verify(eventProducer, times(1)).produce(
                eq("UserActivatedEvent"),
                argThat(event -> hasUserActivatedEventCorrectedData((UserActivatedEvent) event))
        );

        verify(eventStoreRepository, times(1)).save(
                argThat(eventModel -> (
                        eventModel.eventType().equals("UserActivatedEvent")
                        && eventModel.aggregateId().getValue().equals(USER_UUID))
                        && hasUserActivatedEventCorrectedData((UserActivatedEvent) eventModel.eventData())
                )
        );
    }

    private ActivateUserCommandHandler createHandler(
            EventProducer eventProducer,
            EventStoreRepository eventStoreRepository
    ) {
        var eventStore = createEventStore(eventStoreRepository, eventProducer);
        var eventSourcingHandler = createSourcingHandler(eventStore);

        return new ActivateUserCommandHandler(eventSourcingHandler);
    }

    private boolean hasUserActivatedEventCorrectedData(UserActivatedEvent event) {
        return event.getId().equals(ACTIVATE_USER_COMMAND.getUserId().getValue());
    }
}
