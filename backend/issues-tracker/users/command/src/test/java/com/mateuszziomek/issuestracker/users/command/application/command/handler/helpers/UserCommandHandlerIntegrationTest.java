package com.mateuszziomek.issuestracker.users.command.application.command.handler.helpers;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.cqrs.event.store.EventStore;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import com.mateuszziomek.issuestracker.users.command.domain.user.User;

public abstract class UserCommandHandlerIntegrationTest {
    protected EventStore<User> createEventStore(
            EventStoreRepository eventStoreRepository,
            EventProducer eventProducer
    ) {
        return new EventStore<>(eventStoreRepository, eventProducer, User.class);
    }

    protected EventSourcingHandler<User> createSourcingHandler(EventStore<User> eventStore) {
        return new EventSourcingHandler<>(eventStore, User::new);
    }
}
