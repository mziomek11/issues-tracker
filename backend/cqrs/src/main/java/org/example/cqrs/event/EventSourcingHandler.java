package org.example.cqrs.event;

import org.example.cqrs.domain.AggregateId;
import org.example.cqrs.domain.AggregateRoot;

import java.util.Optional;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregateRoot);
    Optional<T> getById(AggregateId id);
}
