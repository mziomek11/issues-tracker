package org.example.issuestracker.issues.command.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.domain.AggregateId;
import org.example.cqrs.domain.AggregateRoot;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.cqrs.event.EventStore;
import org.example.issuestracker.issues.command.domain.issue.Issue;

import java.util.Comparator;
import java.util.Optional;

@RequiredArgsConstructor
public class IssueEventSourcingHandler implements EventSourcingHandler<Issue> {
    private final EventStore eventStore;

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEvents(aggregateRoot.getId(), aggregateRoot.getUncommittedChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public Optional<Issue> getById(AggregateId aggregateId) {
        var aggregate = new Issue();
        var events = eventStore.getEvents(aggregateId);

        if (events.isEmpty()) {
            return Optional.empty();
        }

        aggregate.replayEvents(events);

        var latestVersion = events
                .stream()
                .map(BaseEvent::getVersion)
                .max(Comparator.naturalOrder());

        if (latestVersion.isEmpty()) {
            throw new IllegalStateException("Latest version could not be found");
        }

        aggregate.setVersion(latestVersion.get());

        return Optional.of(aggregate);
    }
}
