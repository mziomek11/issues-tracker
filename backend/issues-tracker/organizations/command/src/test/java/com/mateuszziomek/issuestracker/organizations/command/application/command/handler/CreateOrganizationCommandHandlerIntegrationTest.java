package com.mateuszziomek.issuestracker.organizations.command.application.command.handler;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers.OrganizationCommandHandlerIntegrationTest;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import org.junit.jupiter.api.Test;

import static com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers.OrganizationCommandData.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CreateOrganizationCommandHandlerIntegrationTest extends OrganizationCommandHandlerIntegrationTest {
    @Test
    void creatingOrganizationSavesEventToDbAndSendsMessageToBroker() {
        // Arrange
        var eventProducer = mock(EventProducer.class);
        var eventStoreRepository = mock(EventStoreRepository.class);
        var sut = createHandler(eventProducer, eventStoreRepository);

        // Act
        sut.handle(CREATE_ORGANIZATION_COMMAND);

        // Assert
        verify(eventProducer, times(1)).produce(
                eq("OrganizationCreatedEvent"),
                argThat(event -> hasOrganizationCreatedEventCorrectedData((OrganizationCreatedEvent) event))
        );

        verify(eventStoreRepository, times(1)).save(
                argThat(eventModel -> (
                        eventModel.eventType().equals("OrganizationCreatedEvent")
                        && eventModel.aggregateId().getValue().equals(ORGANIZATION_UUID))
                        && hasOrganizationCreatedEventCorrectedData((OrganizationCreatedEvent) eventModel.eventData())
                )
        );
    }

    private CreateOrganizationCommandHandler createHandler(
            EventProducer eventProducer,
            EventStoreRepository eventStoreRepository
    ) {
        var eventStore = createEventStore(eventStoreRepository, eventProducer);
        var eventSourcingHandler = createSourcingHandler(eventStore);

        return new CreateOrganizationCommandHandler(eventSourcingHandler);
    }

    private boolean hasOrganizationCreatedEventCorrectedData(OrganizationCreatedEvent event) {
        var command = CREATE_ORGANIZATION_COMMAND;

        return (
                event.getId().equals(command.getOrganizationId().getValue())
                && event.getOrganizationOwnerId().equals(command.getOrganizationOwnerId().getValue())
                && event.getOrganizationName().equals(command.getOrganizationName().text())
        );
    }
}
