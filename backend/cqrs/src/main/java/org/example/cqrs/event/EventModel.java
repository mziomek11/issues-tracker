package org.example.cqrs.event;

import org.example.cqrs.domain.AggregateId;

import java.util.Date;
import java.util.UUID;

public record EventModel (
        UUID id,
        Date timestamp,
        AggregateId aggregateId,
        String aggregateType,
        int version,
        String eventType,
        BaseEvent eventData
) {}
