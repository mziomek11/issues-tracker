package com.mateuszziomek.cqrs.event;

import java.util.Objects;
import java.util.UUID;

public abstract class BaseEvent {
    private UUID id;
    private int version;

    protected BaseEvent() {}

    protected BaseEvent(UUID id) {
        this.id = Objects.requireNonNull(id);
    }

    public UUID getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
