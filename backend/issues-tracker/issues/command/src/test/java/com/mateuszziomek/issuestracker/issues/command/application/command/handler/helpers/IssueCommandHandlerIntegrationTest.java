package com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers;

import static com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers.IssueCommandData.*;
import static org.mockito.Mockito.*;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.cqrs.event.store.EventStore;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.command.projection.Organization;
import com.mateuszziomek.issuestracker.issues.command.projection.OrganizationRepository;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;

import java.util.Optional;
import java.util.UUID;

public abstract class IssueCommandHandlerIntegrationTest {
    protected EventStore<Issue> createEventStore(
            EventStoreRepository eventStoreRepository,
            EventProducer eventProducer
    ) {
        return new EventStore<>(eventStoreRepository, eventProducer, Issue.class);
    }

    protected EventSourcingHandler<Issue> createSourcingHandler(EventStore<Issue> eventStore) {
        return new EventSourcingHandler<>(eventStore, Issue::new);
    }

    protected OrganizationRepository createOrganizationRepositoryMock() {
        var organizationRepository = mock(OrganizationRepository.class);

        when(organizationRepository.findById(ORGANIZATION_UUID))
                .thenReturn(Optional.of(createOrganization()));

        return organizationRepository;
    }

    private Organization createOrganization() {
        var organization = Organization.create(
            OrganizationCreatedEvent
                .builder()
                .organizationId(ORGANIZATION_UUID)
                .organizationName("Example name")
                .organizationOwnerId(UUID.randomUUID())
                .build()
        );

        organization.addProject(
            OrganizationProjectCreatedEvent
                .builder()
                .organizationId(ORGANIZATION_UUID)
                .projectId(PROJECT_UUID)
                .projectName("Example name")
                .build()
        );

        organization.joinMember(
            OrganizationMemberJoinedEvent
                .builder()
                .organizationId(ORGANIZATION_UUID)
                .memberId(MEMBER_UUID)
                .build()
        );

        return organization;
    }
}
