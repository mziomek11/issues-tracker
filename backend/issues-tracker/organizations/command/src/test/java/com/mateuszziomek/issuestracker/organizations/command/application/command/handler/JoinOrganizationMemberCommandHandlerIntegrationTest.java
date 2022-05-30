package com.mateuszziomek.issuestracker.organizations.command.application.command.handler;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers.OrganizationCommandHandlerIntegrationTest;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers.OrganizationCommandData.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class JoinOrganizationMemberCommandHandlerIntegrationTest extends OrganizationCommandHandlerIntegrationTest {
    @Test
    void joiningOrganizationMemberSavesEventToDbAndSendsMessageToBroker() {
        // Arrange
        var eventProducer = mock(EventProducer.class);
        var eventStoreRepository = mock(EventStoreRepository.class);
        var sut = createHandler(eventProducer, eventStoreRepository);

        when(eventStoreRepository.findByAggregateId(ORGANIZATION_ID))
                .thenReturn(List.of(ORGANIZATION_CREATED_EVENT_MODEL, ORGANIZATION_MEMBER_INVITED_EVENT_MODEL));

        // Act
        sut.handle(JOIN_ORGANIZATION_MEMBER_COMMAND);

        // Assert
        verify(eventProducer, times(1)).produce(
                eq("OrganizationMemberJoinedEvent"),
                argThat(event -> hasOrganizationMemberJoinedEventCorrectedData((OrganizationMemberJoinedEvent) event))
        );

        verify(eventStoreRepository, times(1)).save(
                argThat(eventModel -> (
                        eventModel.eventType().equals("OrganizationMemberJoinedEvent")
                        && eventModel.aggregateId().getValue().equals(ORGANIZATION_UUID))
                        && hasOrganizationMemberJoinedEventCorrectedData((OrganizationMemberJoinedEvent) eventModel.eventData())
                )
        );
    }

    private JoinOrganizationMemberCommandHandler createHandler(
            EventProducer eventProducer,
            EventStoreRepository eventStoreRepository
    ) {
        var eventStore = createEventStore(eventStoreRepository, eventProducer);
        var eventSourcingHandler = createSourcingHandler(eventStore);

        return new JoinOrganizationMemberCommandHandler(eventSourcingHandler);
    }

    private boolean hasOrganizationMemberJoinedEventCorrectedData(OrganizationMemberJoinedEvent event) {
        var command = JOIN_ORGANIZATION_MEMBER_COMMAND;

        return (
                event.getId().equals(command.getOrganizationId().getValue())
                && event.getMemberId().equals(command.getMemberId().getValue())
        );
    }
}
