package org.example.cqrs.event;

import org.example.cqrs.domain.AggregateId;

import java.util.List;

public interface EventStoreRepository {
    List<EventModel> findByAggregateId(AggregateId aggregateId);
    EventModel save(EventModel eventModel);
}
