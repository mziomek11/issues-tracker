package com.mateuszziomek.cqrs.event.sourcinghandler;

import com.mateuszziomek.cqrs.domain.AggregateConcurrencyException;
import com.mateuszziomek.cqrs.domain.AggregateRoot;
import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.store.EventStore;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.domain.AggregateId;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class EventSourcingHandler<T extends AggregateRoot> {
    private final EventStore<T> eventStore;
    private final Supplier<T> supplier;

    /**
     * @throws AggregateConcurrencyException see {@link EventStore#saveEvents(AggregateId, Iterable, int)}s
     */
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEvents(aggregateRoot.getId(), aggregateRoot.getUncommittedChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

    public Optional<T> getById(AggregateId aggregateId) {
        var aggregate = supplier.get();
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
