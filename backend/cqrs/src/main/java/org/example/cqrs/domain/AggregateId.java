package org.example.cqrs.domain;

import java.util.Objects;
import java.util.UUID;

public abstract class AggregateId {
    private final UUID id;

    protected AggregateId(UUID id) {
        this.id = Objects.requireNonNull(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AggregateId)) return false;
        AggregateId that = (AggregateId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
