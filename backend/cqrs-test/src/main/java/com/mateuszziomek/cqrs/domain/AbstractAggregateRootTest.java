package com.mateuszziomek.cqrs.domain;

import static org.assertj.core.api.Assertions.*;

public abstract class AbstractAggregateRootTest {
    protected void assertThatTheOnlyRaisedEventIs(AggregateRoot aggregateRoot, Class<?> clazz) {
        assertThat(aggregateRoot.getUncommittedChanges().size()).isEqualTo(1);

        var event = aggregateRoot.getUncommittedChanges().get(0);
        assertThat(event).isInstanceOf(clazz);
    }

    protected void assertThatNoEventsAreRaised(AggregateRoot aggregateRoot) {
        assertThatAmountOfRaisedEventsIsEqualTo(aggregateRoot, 0);
    }

    protected void assertThatAmountOfRaisedEventsIsEqualTo(AggregateRoot issue, int amount) {
        assertThat(issue.getUncommittedChanges().size()).isEqualTo(amount);
    }
}
