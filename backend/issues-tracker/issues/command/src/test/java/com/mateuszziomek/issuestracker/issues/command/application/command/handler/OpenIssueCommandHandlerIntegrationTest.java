package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import static com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandData.*;
import static org.mockito.Mockito.*;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.cqrs.event.store.EventStore;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandHandlerIntegrationTest;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.command.infrastructure.gateway.OrganizationGatewayImpl;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueOpenedEvent;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.ReactiveOrganizationRestClient;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class OpenIssueCommandHandlerIntegrationTest extends IssueCommandHandlerIntegrationTest {
    private static final String EVENT_TYPE = "IssueOpenedEvent";

    @Test
    void openingIssueSavesEventToDbAndSendsMessageToBroker() {
        // Arrange
        var eventProducer = mock(EventProducer.class);
        var eventStoreRepository = mock(EventStoreRepository.class);
        var organizationRestClient = createOrganizationRestClientMock();

        when(eventStoreRepository.findByAggregateId(any())).thenReturn(new ArrayList<>());

        var sut = createHandler(
                eventProducer,
                eventStoreRepository,
                organizationRestClient
        );

        // Act
        sut.handle(OPEN_ISSUE_COMMAND);

        // Assert
        verify(eventProducer, times(1)).produce(
                eq(EVENT_TYPE),
                argThat(event -> hasIssueOpenedEventCorrectedData((IssueOpenedEvent) event))
        );

        verify(eventStoreRepository, times(1)).save(
                argThat(eventModel -> (
                        eventModel.eventType().equals(EVENT_TYPE)
                        && eventModel.aggregateId().getValue().equals(ISSUE_UUID))
                        && hasIssueOpenedEventCorrectedData((IssueOpenedEvent) eventModel.eventData())
                )
        );
    }

    private OpenIssueCommandHandler createHandler(
            EventProducer eventProducer,
            EventStoreRepository eventStoreRepository,
            ReactiveOrganizationRestClient organizationRestClient
    ) {
        var eventStore = new EventStore<>(eventStoreRepository, eventProducer, Issue.class);
        var eventSourcingHandler = new EventSourcingHandler<>(eventStore, Issue::new);
        var organizationGateway = new OrganizationGatewayImpl(organizationRestClient);

        return new OpenIssueCommandHandler(eventSourcingHandler, organizationGateway);
    }

    private boolean hasIssueOpenedEventCorrectedData(IssueOpenedEvent event) {
        var command = OPEN_ISSUE_COMMAND;
        var organizationDetails = command.getOrganizationDetails();

        return (
                event.getIssueContent().equals(command.getIssueContent().text())
                && event.getIssueName().equals(command.getIssueName().text())
                && event.getIssueType().equals(command.getIssueType())
                && event.getMemberId().equals(organizationDetails.memberId().getValue())
                && event.getOrganizationId().equals(organizationDetails.organizationId().getValue())
                && event.getProjectId().equals(organizationDetails.projectId().getValue())
        );
    }
}
