package org.example.cqrs.domain;

import java.util.Objects;
import java.util.UUID;

public abstract class AggregateId {
    public final UUID id;

    protected AggregateId(UUID id) {
        this.id = Objects.requireNonNull(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
