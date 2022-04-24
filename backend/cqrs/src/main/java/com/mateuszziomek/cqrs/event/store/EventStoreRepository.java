package com.mateuszziomek.cqrs.event.store;

import com.mateuszziomek.cqrs.domain.AggregateId;
import com.mateuszziomek.cqrs.event.EventModel;

import java.util.List;

public interface EventStoreRepository {
    List<EventModel> findByAggregateId(AggregateId aggregateId);
    EventModel save(EventModel eventModel);
}
