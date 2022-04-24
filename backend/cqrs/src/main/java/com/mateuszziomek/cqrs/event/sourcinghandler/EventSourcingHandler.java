package com.mateuszziomek.cqrs.event.sourcinghandler;

import com.mateuszziomek.cqrs.domain.AggregateConcurrencyException;
import com.mateuszziomek.cqrs.domain.AggregateRoot;
import com.mateuszziomek.cqrs.event.store.EventStore;
import com.mateuszziomek.cqrs.domain.AggregateId;

import java.util.Optional;

public interface EventSourcingHandler<T extends AggregateRoot> {
    /**
     * @throws AggregateConcurrencyException see {@link EventStore#saveEvents(AggregateId, Iterable, int)}
     */
    void save(AggregateRoot aggregateRoot);
    Optional<T> getById(AggregateId id);
}
