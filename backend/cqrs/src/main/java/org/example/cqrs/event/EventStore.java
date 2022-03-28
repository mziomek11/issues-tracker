package org.example.cqrs.event;

import org.example.cqrs.domain.AggregateId;

import java.util.List;

public interface EventStore {
    void saveEvents(AggregateId aggregateId, Iterable<BaseEvent> events, int exceptedVersion);
    List<BaseEvent> getEvents(AggregateId aggregateId);
}
