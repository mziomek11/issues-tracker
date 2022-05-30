package com.mateuszziomek.issuestracker.organizations.command.application.command.handler.helpers;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.cqrs.event.store.EventStore;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.Organization;

public abstract class OrganizationCommandHandlerIntegrationTest {
    protected EventStore<Organization> createEventStore(
            EventStoreRepository eventStoreRepository,
            EventProducer eventProducer
    ) {
        return new EventStore<>(eventStoreRepository, eventProducer, Organization.class);
    }

    protected EventSourcingHandler<Organization> createSourcingHandler(EventStore<Organization> eventStore) {
        return new EventSourcingHandler<>(eventStore, Organization::new);
    }
}
