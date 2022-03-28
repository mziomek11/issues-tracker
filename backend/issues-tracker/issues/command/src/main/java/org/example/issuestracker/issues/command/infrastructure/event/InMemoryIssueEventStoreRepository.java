package org.example.issuestracker.issues.command.infrastructure.event;

import org.example.cqrs.domain.AggregateId;
import org.example.cqrs.event.EventModel;
import org.example.cqrs.event.EventStoreRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryIssueEventStoreRepository implements EventStoreRepository {
    private final List<EventModel> events = new ArrayList<>();

    @Override
    public List<EventModel> findByAggregateId(AggregateId aggregateId) {
        return events
                .stream()
                .filter(eventModel -> eventModel.aggregateId().equals(aggregateId))
                .toList();
    }

    @Override
    public EventModel save(EventModel eventModel) {
        events.add(eventModel);

        return eventModel;
    }
}
