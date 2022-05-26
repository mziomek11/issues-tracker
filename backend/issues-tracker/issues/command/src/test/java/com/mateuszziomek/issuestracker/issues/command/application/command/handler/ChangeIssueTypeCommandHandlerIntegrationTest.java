package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandHandlerIntegrationTest;
import com.mateuszziomek.issuestracker.issues.command.infrastructure.gateway.OrganizationGatewayImpl;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueTypeChangedEvent;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.ReactiveOrganizationRestClient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandData.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ChangeIssueTypeCommandHandlerIntegrationTest extends IssueCommandHandlerIntegrationTest {
    @Test
    void changingIssueTypeSavesEventToDbAndSendsMessageToBroker() {
        // Arrange
        var eventProducer = mock(EventProducer.class);
        var eventStoreRepository = mock(EventStoreRepository.class);
        var organizationRestClient = createOrganizationRestClientMock();

        when(eventStoreRepository.findByAggregateId(ISSUE_ID)).thenReturn(List.of(ISSUE_OPENED_EVENT_MODEL));

        var sut = createHandler(
                eventProducer,
                eventStoreRepository,
                organizationRestClient
        );

        // Act
        sut.handle(CHANGE_ISSUE_TYPE_COMMAND);

        // Assert
        verify(eventProducer, times(1)).produce(
                eq("IssueTypeChangedEvent"),
                argThat(event -> hasIssueTypeChangedEventCorrectedData((IssueTypeChangedEvent) event))
        );

        verify(eventStoreRepository, times(1)).save(
                argThat(eventModel -> (
                        eventModel.eventType().equals("IssueTypeChangedEvent")
                        && eventModel.aggregateId().getValue().equals(ISSUE_UUID))
                        && hasIssueTypeChangedEventCorrectedData((IssueTypeChangedEvent) eventModel.eventData())
                )
        );
    }

    private ChangeIssueTypeCommandHandler createHandler(
            EventProducer eventProducer,
            EventStoreRepository eventStoreRepository,
            ReactiveOrganizationRestClient organizationRestClient
    ) {
        var eventStore = createEventStore(eventStoreRepository, eventProducer);
        var eventSourcingHandler = createSourcingHandler(eventStore);
        var organizationGateway = new OrganizationGatewayImpl(organizationRestClient);

        return new ChangeIssueTypeCommandHandler(eventSourcingHandler, organizationGateway);
    }

    private boolean hasIssueTypeChangedEventCorrectedData(IssueTypeChangedEvent event) {
        var command = CHANGE_ISSUE_TYPE_COMMAND;
        var organizationDetails = command.getOrganizationDetails();

        return (
                event.getId().equals(command.getIssueId().getValue())
                        && event.getIssueType().equals(command.getIssueType())
                        && event.getMemberId().equals(organizationDetails.memberId().getValue())
                        && event.getOrganizationId().equals(organizationDetails.organizationId().getValue())
                        && event.getProjectId().equals(organizationDetails.projectId().getValue())
        );
    }
}
