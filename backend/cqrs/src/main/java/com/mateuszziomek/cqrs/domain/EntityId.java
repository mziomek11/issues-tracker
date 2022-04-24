package com.mateuszziomek.cqrs.domain;

import java.util.Objects;
import java.util.UUID;

public abstract class EntityId {
    private final UUID id;

    protected EntityId(UUID id) {
        this.id = Objects.requireNonNull(id);
    }

    public UUID getValue() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityId)) return false;
        EntityId that = (EntityId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
