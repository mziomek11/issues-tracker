package com.mateuszziomek.cqrs.event.store;

import com.mateuszziomek.cqrs.domain.AggregateConcurrencyException;
import com.mateuszziomek.cqrs.domain.AggregateId;
import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.EventModel;
import com.mateuszziomek.cqrs.event.producer.EventProducer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventStoreTest {
    @Test
    void concurrencyExceptionIsThrownWhenAggregateVersionInRepositoryIsDifferentThanExpected() {
        // Arrange
        var eventStoreRepository = mock(EventStoreRepository.class);
        var eventProducer = mock(EventProducer.class);
        var sut = new EventStore(eventStoreRepository, eventProducer, Object.class);
        var aggregateId = new TestAggregateId();
        var persistedEvents = List.of(
                createEvent(aggregateId, 0),
                createEvent(aggregateId, 1)
        );
        var eventsToPersist = List.of(new TestEvent("Another name"));

        when(eventStoreRepository.findByAggregateId(aggregateId)).thenReturn(persistedEvents);

        // Assert
        assertThatExceptionOfType(AggregateConcurrencyException.class)
                .isThrownBy(() -> sut.saveEvents(aggregateId, eventsToPersist, 0));

        verify(eventStoreRepository, times(0)).save(any());
        verify(eventProducer, times(0)).produce(any(), any());
    }

    @Test
    void eventsAreSaved() {
        // Arrange
        var eventStoreRepository = mock(EventStoreRepository.class);
        var eventProducer = mock(EventProducer.class);
        var sut = new EventStore(eventStoreRepository, eventProducer, Object.class);
        var aggregateId = new TestAggregateId();
        var persistedEvents = List.of(
                createEvent(aggregateId, 0),
                createEvent(aggregateId, 1)
        );

        var firstEventToPersist = new TestEvent("First test event");
        var secondEventToPersist = new TestEvent("Second test event");
        var eventsToPersist = List.of(firstEventToPersist, secondEventToPersist);

        when(eventStoreRepository.findByAggregateId(aggregateId)).thenReturn(persistedEvents);

        // Act
        sut.saveEvents(aggregateId, eventsToPersist, 1);

        // Assert
        var eventStoreRepositoryEventCaptor = ArgumentCaptor.forClass(EventModel.class);
        verify(eventStoreRepository, times(2)).save(eventStoreRepositoryEventCaptor.capture());
        var firstRepoEvent = eventStoreRepositoryEventCaptor.getAllValues().get(0);
        var secondRepoEvent = eventStoreRepositoryEventCaptor.getAllValues().get(1);

        assertThat(firstRepoEvent.eventType()).isEqualTo("TestEvent");
        assertThat(firstRepoEvent.eventData().getVersion()).isEqualTo(2);
        assertThat(((TestEvent) firstRepoEvent.eventData()).name).isEqualTo("First test event");
        assertThat(firstRepoEvent.aggregateId()).isEqualTo(aggregateId);
        assertThat(firstRepoEvent.version()).isEqualTo(2);
        assertThat(firstRepoEvent.aggregateType()).isEqualTo("java.lang.Object");

        assertThat(secondRepoEvent.eventType()).isEqualTo("TestEvent");
        assertThat(secondRepoEvent.eventData().getVersion()).isEqualTo(3);
        assertThat(((TestEvent) secondRepoEvent.eventData()).name).isEqualTo("Second test event");
        assertThat(secondRepoEvent.aggregateId()).isEqualTo(aggregateId);
        assertThat(secondRepoEvent.version()).isEqualTo(3);
        assertThat(secondRepoEvent.aggregateType()).isEqualTo("java.lang.Object");
    }

    @Test
    void eventsAreSentToBroker() {
        // Arrange
        var eventStoreRepository = mock(EventStoreRepository.class);
        var eventProducer = mock(EventProducer.class);
        var sut = new EventStore(eventStoreRepository, eventProducer, Object.class);
        var aggregateId = new TestAggregateId();
        var persistedEvents = List.of(
                createEvent(aggregateId, 0),
                createEvent(aggregateId, 1)
        );

        var firstEventToPersist = new TestEvent("First test event");
        var secondEventToPersist = new TestEvent("Second test event");
        var eventsToPersist = List.of(firstEventToPersist, secondEventToPersist);

        when(eventStoreRepository.findByAggregateId(aggregateId)).thenReturn(persistedEvents);

        // Act
        sut.saveEvents(aggregateId, eventsToPersist, 1);

        // Assert
        var eventProducerTopicCaptor = ArgumentCaptor.forClass(String.class);
        var eventProducerEventCaptor = ArgumentCaptor.forClass(BaseEvent.class);
        verify(eventProducer, times(2)).produce(
                eventProducerTopicCaptor.capture(),
                eventProducerEventCaptor.capture()
        );

        assertThat(eventProducerTopicCaptor.getAllValues().get(0)).isEqualTo("TestEvent");
        assertThat(eventProducerTopicCaptor.getAllValues().get(1)).isEqualTo("TestEvent");

        var firstProducerEvent = (TestEvent) eventProducerEventCaptor.getAllValues().get(0);
        var secondProducerEvent = (TestEvent) eventProducerEventCaptor.getAllValues().get(1);

        assertThat(firstProducerEvent.name).isEqualTo("First test event");
        assertThat(secondProducerEvent.name).isEqualTo("Second test event");
    }

    private EventModel createEvent(AggregateId aggregateId, int version) {
        return new EventModel(
                UUID.randomUUID(),
                new Date(),
                aggregateId,
                "java.lang.Object",
                version,
                "EventType",
                new TestEvent("Example name")
        );
    }

    @RequiredArgsConstructor
    private static class TestEvent extends BaseEvent {
        private final String name;
    }

    private static class TestAggregateId extends AggregateId {
        protected TestAggregateId() {
            super(UUID.randomUUID());
        }
    }
}
