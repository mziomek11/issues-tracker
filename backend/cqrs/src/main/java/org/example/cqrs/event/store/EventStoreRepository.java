package org.example.cqrs.event.store;

import org.example.cqrs.domain.AggregateId;
import org.example.cqrs.event.EventModel;

import java.util.List;

public interface EventStoreRepository {
    List<EventModel> findByAggregateId(AggregateId aggregateId);
    EventModel save(EventModel eventModel);
}
