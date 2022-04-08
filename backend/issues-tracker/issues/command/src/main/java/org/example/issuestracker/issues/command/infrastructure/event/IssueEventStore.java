package org.example.issuestracker.issues.command.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.domain.AggregateId;
import org.example.cqrs.event.*;
import org.example.cqrs.domain.AggregateConcurrencyException;
import org.example.issuestracker.issues.command.domain.issue.Issue;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class IssueEventStore implements EventStore {
    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;

    @Override
    public void saveEvents(AggregateId aggregateId, Iterable<BaseEvent> events, int exceptedVersion) {
        var persistedEvents = eventStoreRepository.findByAggregateId(aggregateId);

        if (exceptedVersion != -1 && persistedEvents.get(persistedEvents.size() - 1).version() != exceptedVersion) {
            throw new AggregateConcurrencyException(aggregateId);
        }

        var version = exceptedVersion;
        for (var event: events) {
            version += 1;
            event.setVersion(version);
            var eventModel = new EventModel(
                    UUID.randomUUID(),
                    new Date(),
                    aggregateId,
                    Issue.class.getTypeName(),
                    version,
                    event.getClass().getTypeName(),
                    event
            );

            eventStoreRepository.save(eventModel);
            eventProducer.produce(event.getClass().getName(), event);
        }
    }

    @Override
    public List<BaseEvent> getEvents(AggregateId aggregateId) {
        var events = eventStoreRepository.findByAggregateId(aggregateId);

        return events
                .stream()
                .map(EventModel::eventData)
                .toList();
    }
}
