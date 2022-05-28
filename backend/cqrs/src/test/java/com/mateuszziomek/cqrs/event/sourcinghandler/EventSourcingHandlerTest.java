package com.mateuszziomek.cqrs.event.sourcinghandler;

import com.mateuszziomek.cqrs.domain.AggregateId;
import com.mateuszziomek.cqrs.domain.AggregateRoot;
import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.store.EventStore;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventSourcingHandlerTest {
    @Test
    void savesEventsToStore() {
        // Arrange
        var eventStore = mock(EventStore.class);
        var sut = new EventSourcingHandler<>(eventStore, TestAggregate::new);
        var aggregate = new TestAggregate();
        aggregate.setVersion(5);
        aggregate.doActionThatGeneratesTestEvent();
        aggregate.doActionThatGeneratesAnotherTestEvent();

        // Act
        sut.save(aggregate);

        // Assert
        verify(eventStore, times(1)).saveEvents(eq(aggregate.getId()), any(), eq(5));

        var events = aggregate.eventsBeforeMarkingAsCommitted;
        assertThat(events.size()).isEqualTo(2);

        var testEvent = (TestEvent) events.get(0);
        var anotherTestEvent = (AnotherTestEvent) events.get(1);

        assertThat(testEvent.isTestEvent()).isTrue();
        assertThat(anotherTestEvent.isAnotherTestEvent()).isTrue();
    }

    @Test
    void markEventsAsCommitted() {
        // Arrange
        var eventStore = mock(EventStore.class);
        var sut = new EventSourcingHandler<>(eventStore, TestAggregate::new);
        var aggregate = new TestAggregate();
        aggregate.setVersion(5);
        aggregate.doActionThatGeneratesTestEvent();
        aggregate.doActionThatGeneratesAnotherTestEvent();

        // Act
        sut.save(aggregate);

        // Assert
        assertThat(aggregate.getUncommittedChanges().size()).isZero();
    }

    @Test
    void replaysEventsOnAggregate() {
        // Arrange
        var eventStore = mock(EventStore.class);
        var sut = new EventSourcingHandler<>(eventStore, TestAggregate::new);
        var aggregateId = new TestAggregateId();
        var testEvent = new TestEvent();
        var anotherTestEvent = new AnotherTestEvent();
        anotherTestEvent.setVersion(5);

        when(eventStore.getEvents(any())).thenReturn(List.of(testEvent, anotherTestEvent));

        // Act
        var optionalAggregate = sut.getById(aggregateId);

        // Assert
        assertThat(optionalAggregate).isPresent();

        var aggregate = (TestAggregate) optionalAggregate.get();
        assertThat(aggregate.testEventCalls).isEqualTo(1);
        assertThat(aggregate.anotherTestEventCalls).isEqualTo(1);
        assertThat(aggregate.getUncommittedChanges()).isEmpty();
    }

    @Test
    void returnsEmptyResultWhenNoEventsAreFound() {
        // Arrange
        var eventStore = mock(EventStore.class);
        var sut = new EventSourcingHandler<>(eventStore, TestAggregate::new);
        var aggregateId = new TestAggregateId();

        when(eventStore.getEvents(aggregateId)).thenReturn(List.of());

        // Act
        var aggregate = sut.getById(aggregateId);

        // Assert
        assertThat(aggregate).isEmpty();
    }

    public static class TestAggregate extends AggregateRoot {
        private final TestAggregateId id = new TestAggregateId();
        private int testEventCalls = 0;
        private int anotherTestEventCalls = 0;
        private List<BaseEvent> eventsBeforeMarkingAsCommitted = List.of();

        @Override
        public AggregateId getId() {
            return id;
        }

        @Override
        public void markChangesAsCommitted() {
            eventsBeforeMarkingAsCommitted = new ArrayList<>(getUncommittedChanges());
            super.markChangesAsCommitted();
        }

        public void doActionThatGeneratesTestEvent() {
            raiseEvent(new TestEvent());
        }

        public void doActionThatGeneratesAnotherTestEvent() {
            raiseEvent(new AnotherTestEvent());
        }

        public void on(TestEvent event) {
            testEventCalls += 1;
        }

        public void on(AnotherTestEvent event) {
            anotherTestEventCalls += 1;
        }
    }

    private static class TestAggregateId extends AggregateId {
        protected TestAggregateId() {
            super(UUID.randomUUID());
        }
    }

    private static class TestEvent extends BaseEvent {
        public boolean isTestEvent() {
            return true;
        }
    }

    private static class AnotherTestEvent extends BaseEvent {
        public boolean isAnotherTestEvent() {
            return true;
        }
    }
}
