package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import static com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandData.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.issues.command.infrastructure.gateway.OrganizationGatewayImpl;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueClosedEvent;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.ReactiveOrganizationRestClient;
import org.junit.jupiter.api.Test;

import java.util.List;

class CloseIssueCommandHandlerIntegrationTest extends OpenIssueCommandHandlerIntegrationTest {
    @Test
    void closingIssueSavesEventToDbAndSendsMessageToBroker() {
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
        sut.handle(CLOSE_ISSUE_COMMAND);

        // Assert
        verify(eventProducer, times(1)).produce(
                eq("IssueClosedEvent"),
                argThat(event -> hasIssueClosedEventCorrectedData((IssueClosedEvent) event))
        );

        verify(eventStoreRepository, times(1)).save(
                argThat(eventModel -> (
                        eventModel.eventType().equals("IssueClosedEvent")
                        && eventModel.aggregateId().getValue().equals(ISSUE_UUID))
                        && hasIssueClosedEventCorrectedData((IssueClosedEvent) eventModel.eventData())
                )
        );
    }

    private CloseIssueCommandHandler createHandler(
            EventProducer eventProducer,
            EventStoreRepository eventStoreRepository,
            ReactiveOrganizationRestClient organizationRestClient
    ) {
        var eventStore = createEventStore(eventStoreRepository, eventProducer);
        var eventSourcingHandler = createSourcingHandler(eventStore);
        var organizationGateway = new OrganizationGatewayImpl(organizationRestClient);

        return new CloseIssueCommandHandler(eventSourcingHandler, organizationGateway);
    }

    private boolean hasIssueClosedEventCorrectedData(IssueClosedEvent event) {
        var command = CLOSE_ISSUE_COMMAND;
        var organizationDetails = command.getOrganizationDetails();

        return (
                event.getId().equals(command.getIssueId().getValue())
                && event.getMemberId().equals(organizationDetails.memberId().getValue())
                && event.getOrganizationId().equals(organizationDetails.organizationId().getValue())
                && event.getProjectId().equals(organizationDetails.projectId().getValue())
        );
    }
}
