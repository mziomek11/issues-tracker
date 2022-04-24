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
public class DefaultEventSourcingHandler<T extends AggregateRoot> implements EventSourcingHandler<T> {
    private final EventStore eventStore;
    private final Supplier<T> supplier;

    /**
     * @throws AggregateConcurrencyException see {@link EventSourcingHandler#save(AggregateRoot)}
     */
    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEvents(aggregateRoot.getId(), aggregateRoot.getUncommittedChanges(), aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
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
