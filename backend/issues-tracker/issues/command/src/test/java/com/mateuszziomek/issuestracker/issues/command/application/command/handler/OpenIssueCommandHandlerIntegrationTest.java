package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import static com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandData.*;
import static org.mockito.Mockito.*;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandHandlerIntegrationTest;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.OrganizationService;
import com.mateuszziomek.issuestracker.issues.command.projection.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueOpenedEvent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class OpenIssueCommandHandlerIntegrationTest extends IssueCommandHandlerIntegrationTest {
    @Test
    void openingIssueSavesEventToDbAndSendsMessageToBroker() {
        // Arrange
        var eventProducer = mock(EventProducer.class);
        var eventStoreRepository = mock(EventStoreRepository.class);
        var organizationRepository = createOrganizationRepositoryMock();

        when(eventStoreRepository.findByAggregateId(any())).thenReturn(new ArrayList<>());

        var sut = createHandler(
                eventProducer,
                eventStoreRepository,
                organizationRepository
        );

        // Act
        sut.handle(OPEN_ISSUE_COMMAND);

        // Assert
        verify(eventProducer, times(1)).produce(
                eq("IssueOpenedEvent"),
                argThat(event -> hasIssueOpenedEventCorrectedData((IssueOpenedEvent) event))
        );

        verify(eventStoreRepository, times(1)).save(
                argThat(eventModel -> (
                        eventModel.eventType().equals("IssueOpenedEvent")
                        && eventModel.aggregateId().getValue().equals(ISSUE_UUID))
                        && hasIssueOpenedEventCorrectedData((IssueOpenedEvent) eventModel.eventData())
                )
        );
    }

    private OpenIssueCommandHandler createHandler(
            EventProducer eventProducer,
            EventStoreRepository eventStoreRepository,
            OrganizationRepository organizationRepository
    ) {
        var eventStore = createEventStore(eventStoreRepository, eventProducer);
        var eventSourcingHandler = createSourcingHandler(eventStore);
        var organizationService = new OrganizationService(organizationRepository);

        return new OpenIssueCommandHandler(eventSourcingHandler, organizationService);
    }

    private boolean hasIssueOpenedEventCorrectedData(IssueOpenedEvent event) {
        var command = OPEN_ISSUE_COMMAND;
        var organizationDetails = command.getOrganizationDetails();

        return (
                event.getId().equals(command.getIssueId().getValue())
                && event.getIssueContent().equals(command.getIssueContent().text())
                && event.getIssueName().equals(command.getIssueName().text())
                && event.getIssueType().equals(command.getIssueType())
                && event.getMemberId().equals(organizationDetails.memberId().getValue())
                && event.getOrganizationId().equals(organizationDetails.organizationId().getValue())
                && event.getProjectId().equals(organizationDetails.projectId().getValue())
        );
    }
}
