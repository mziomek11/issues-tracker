package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import static org.mockito.Mockito.*;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.cqrs.event.store.EventStore;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.issues.command.application.command.OpenIssueCommand;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import com.mateuszziomek.issuestracker.issues.command.infrastructure.gateway.OrganizationGatewayImpl;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueOpenedEvent;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;
import com.mateuszziomek.issuestracker.shared.infrastructure.restclient.organization.ReactiveOrganizationRestClient;
import com.mateuszziomek.issuestracker.shared.readmodel.organization.DetailsOrganization;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OpenIssueCommandHandlerIntegrationTest {
    private static final String EVENT_TYPE = "IssueOpenedEvent";

    @Test
    public void openingIssueSavesEventToDbAndSendsMessageToBroker() {
        // Arrange
        var command = createCommand();
        var eventProducer = mock(EventProducer.class);
        var eventStoreRepository = mock(EventStoreRepository.class);
        var organizationRestClient = mock(ReactiveOrganizationRestClient.class);

        when(organizationRestClient.getOrganizationById(command.getOrganizationDetails().organizationId().getValue()))
                .thenReturn(Mono.just(createDetailsOrganization(command.getOrganizationDetails())));

        when(eventStoreRepository.findByAggregateId(any())).thenReturn(new ArrayList<>());

        var sut = createHandler(
                eventProducer,
                eventStoreRepository,
                organizationRestClient
        );

        // Act
        sut.handle(command);

        // Assert
        verify(eventProducer, times(1)).produce(
                eq(EVENT_TYPE),
                argThat(event -> hasIssueOpenedEventCorrectedData((IssueOpenedEvent) event, command))
        );

        verify(eventStoreRepository, times(1)).save(
                argThat(eventModel -> (
                        eventModel.eventType().equals(EVENT_TYPE)
                        && eventModel.aggregateId().getValue().equals(command.getIssueId().getValue()))
                        && hasIssueOpenedEventCorrectedData((IssueOpenedEvent) eventModel.eventData(), command)
                )
        );
    }

    private OpenIssueCommandHandler createHandler(
            EventProducer eventProducer,
            EventStoreRepository eventStoreRepository,
            ReactiveOrganizationRestClient organizationRestClient
    ) {
        var eventStore = new EventStore(eventStoreRepository, eventProducer, Issue.class);
        var eventSourcingHandler = new EventSourcingHandler<>(eventStore, Issue::new);
        var organizationGateway = new OrganizationGatewayImpl(organizationRestClient);

        return new OpenIssueCommandHandler(eventSourcingHandler, organizationGateway);
    }

    private DetailsOrganization createDetailsOrganization(IssueOrganizationDetails organizationDetails) {
        var member = DetailsOrganization.Member
                .builder()
                .id(organizationDetails.memberId().getValue())
                .build();

        var project = DetailsOrganization.Project
                .builder()
                .id(organizationDetails.projectId().getValue())
                .build();

        return DetailsOrganization
                .builder()
                .id(organizationDetails.organizationId().getValue())
                .members(List.of(member))
                .projects(List.of(project))
                .build();
    }

    private OpenIssueCommand createCommand() {
        var organizationDetails = new IssueOrganizationDetails(
                new OrganizationId(UUID.randomUUID()),
                new OrganizationProjectId(UUID.randomUUID()),
                new OrganizationMemberId(UUID.randomUUID())
        );

        return OpenIssueCommand
                .builder()
                .issueId(UUID.randomUUID())
                .issueContent("Example content")
                .issueName("example name")
                .issueType(IssueType.ENHANCEMENT)
                .organizationDetails(organizationDetails)
                .build();
    }

    private boolean hasIssueOpenedEventCorrectedData(IssueOpenedEvent event, OpenIssueCommand command) {
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
