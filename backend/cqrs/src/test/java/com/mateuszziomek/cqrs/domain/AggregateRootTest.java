package com.mateuszziomek.cqrs.domain;

import com.mateuszziomek.cqrs.event.BaseEvent;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class AggregateRootTest {
    @Test
    void raisingEventCallsAggregateOnMethod() {
        // Arrange
        var sut = new TestAggregate();
        var event = new TestEvent();

        // Act
        sut.raiseEvent(event);

        // Assert
        var onCalls = sut.getOnCalls();
        assertThat(onCalls.size()).isEqualTo(1);
        assertThat(onCalls.get(0)).isEqualTo(event);
    }

    @Test
    void raisedEventsAreNotCommitted() {
        // Arrange
        var sut = new TestAggregate();
        var event = new TestEvent();

        // Act
        sut.raiseEvent(event);

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isEqualTo(1);
        assertThat(sut.getUncommittedChanges().get(0)).isEqualTo(event);
    }

    @Test
    void changesCanBeMarkedAsCommitted() {
        // Arrange
        var sut = new TestAggregate();
        var event = new TestEvent();
        sut.raiseEvent(event);

        // Act
        sut.markChangesAsCommitted();

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    @Test
    void eventsCanBeReplayed() {
        // Arrange
        var sut = new TestAggregate();
        var firstEvent = new TestEvent();
        var secondEvent = new TestEvent();

        // Act
        sut.replayEvents(List.of(firstEvent, secondEvent));

        // Assert
        var onCalls = sut.getOnCalls();
        assertThat(onCalls.size()).isEqualTo(2);
        assertThat(onCalls.get(0)).isEqualTo(firstEvent);
        assertThat(onCalls.get(1)).isEqualTo(secondEvent);
    }

    @Test
    void replayedEventAreCommitted() {
        // Arrange
        var sut = new TestAggregate();
        var firstEvent = new TestEvent();
        var secondEvent = new TestEvent();

        // Act
        sut.replayEvents(List.of(firstEvent, secondEvent));

        // Assert
        assertThat(sut.getUncommittedChanges().size()).isZero();
    }

    private static class TestEvent extends BaseEvent {}

    @Getter
    private static class TestAggregate extends AggregateRoot {
        private final TestAggregateId aggregateId = new TestAggregateId();
        private final List<BaseEvent> onCalls = new ArrayList<>();

        @Override
        public AggregateId getId() {
            return aggregateId;
        }

        public void on(TestEvent event) {
            onCalls.add(event);
        }
    }

    private static class TestAggregateId extends AggregateId {
        protected TestAggregateId() {
            super(UUID.randomUUID());
        }
    }
}
