package org.example.cqrs.event;

import org.example.cqrs.domain.AggregateConcurrencyException;
import org.example.cqrs.domain.AggregateId;
import org.example.cqrs.domain.AggregateRoot;

import java.util.Optional;

public interface EventSourcingHandler<T> {
    /**
     * @throws AggregateConcurrencyException see {@link EventStore#saveEvents(AggregateId, Iterable, int)}
     */
    void save(AggregateRoot aggregateRoot);
    Optional<T> getById(AggregateId id);
}
