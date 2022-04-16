package org.example.cqrs.event.sourcinghandler;

import org.example.cqrs.domain.AggregateConcurrencyException;
import org.example.cqrs.domain.AggregateId;
import org.example.cqrs.domain.AggregateRoot;
import org.example.cqrs.event.store.EventStore;

import java.util.Optional;

public interface EventSourcingHandler<T extends AggregateRoot> {
    /**
     * @throws AggregateConcurrencyException see {@link EventStore#saveEvents(AggregateId, Iterable, int)}
     */
    void save(AggregateRoot aggregateRoot);
    Optional<T> getById(AggregateId id);
}
