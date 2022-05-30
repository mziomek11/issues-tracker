package com.mateuszziomek.issuestracker.organizations.command.application.command.handler;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers.OrganizationCommandHandlerIntegrationTest;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers.OrganizationCommandData.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CreateOrganizationProjectCommandHandlerIntegrationTest extends OrganizationCommandHandlerIntegrationTest {
    @Test
    void creatingOrganizationProjectSavesEventToDbAndSendsMessageToBroker() {
        // Arrange
        var eventProducer = mock(EventProducer.class);
        var eventStoreRepository = mock(EventStoreRepository.class);
        var sut = createHandler(eventProducer, eventStoreRepository);

        when(eventStoreRepository.findByAggregateId(ORGANIZATION_ID))
                .thenReturn(List.of(ORGANIZATION_CREATED_EVENT_MODEL));

        // Act
        sut.handle(CREATE_ORGANIZATION_PROJECT_COMMAND);

        // Assert
        verify(eventProducer, times(1)).produce(
                eq("OrganizationProjectCreatedEvent"),
                argThat(event -> hasOrganizationProjectCreatedEventCorrectedData((OrganizationProjectCreatedEvent) event))
        );

        verify(eventStoreRepository, times(1)).save(
                argThat(eventModel -> (
                        eventModel.eventType().equals("OrganizationProjectCreatedEvent")
                        && eventModel.aggregateId().getValue().equals(ORGANIZATION_UUID))
                        && hasOrganizationProjectCreatedEventCorrectedData((OrganizationProjectCreatedEvent) eventModel.eventData())
                )
        );
    }

    private CreateOrganizationProjectCommandHandler createHandler(
            EventProducer eventProducer,
            EventStoreRepository eventStoreRepository
    ) {
        var eventStore = createEventStore(eventStoreRepository, eventProducer);
        var eventSourcingHandler = createSourcingHandler(eventStore);

        return new CreateOrganizationProjectCommandHandler(eventSourcingHandler);
    }

    private boolean hasOrganizationProjectCreatedEventCorrectedData(OrganizationProjectCreatedEvent event) {
        var command = CREATE_ORGANIZATION_PROJECT_COMMAND;

        return (
                event.getId().equals(command.getOrganizationId().getValue())
                && event.getProjectId().equals(command.getProjectId().getValue())
                && event.getProjectName().equals(command.getProjectName().text())
        );
    }
}
