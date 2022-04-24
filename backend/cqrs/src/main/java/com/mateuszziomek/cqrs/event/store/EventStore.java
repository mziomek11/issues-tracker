package com.mateuszziomek.cqrs.event.store;

import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.domain.AggregateConcurrencyException;
import com.mateuszziomek.cqrs.domain.AggregateId;

import java.util.List;

public interface EventStore {
    /**
     * @throws AggregateConcurrencyException if latest version of aggregate is different from excepted
     */
    void saveEvents(AggregateId aggregateId, Iterable<BaseEvent> events, int exceptedVersion);
    List<BaseEvent> getEvents(AggregateId aggregateId);
}
