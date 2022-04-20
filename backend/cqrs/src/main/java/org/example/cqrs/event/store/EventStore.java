package org.example.cqrs.event.store;

import org.example.cqrs.domain.AggregateConcurrencyException;
import org.example.cqrs.domain.AggregateId;
import org.example.cqrs.event.BaseEvent;

import java.util.List;

public interface EventStore {
    /**
     * @throws AggregateConcurrencyException if latest version of aggregate is different from excepted
     */
    void saveEvents(AggregateId aggregateId, Iterable<BaseEvent> events, int exceptedVersion);
    List<BaseEvent> getEvents(AggregateId aggregateId);
}