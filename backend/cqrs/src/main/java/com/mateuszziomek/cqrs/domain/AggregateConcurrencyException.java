package com.mateuszziomek.cqrs.domain;

public class AggregateConcurrencyException extends RuntimeException {
    private final AggregateId aggregateId;

    public AggregateConcurrencyException(AggregateId aggregateId) {
        this.aggregateId = aggregateId;
    }

    public AggregateId getAggregateId() {
        return aggregateId;
    }
}
