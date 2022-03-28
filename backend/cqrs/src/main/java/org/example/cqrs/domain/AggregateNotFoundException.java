package org.example.cqrs.domain;

public class AggregateNotFoundException extends RuntimeException {
    private final AggregateId aggregateId;

    public AggregateNotFoundException(AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }

    public AggregateId getAggregateId() {
        return aggregateId;
    }
}
