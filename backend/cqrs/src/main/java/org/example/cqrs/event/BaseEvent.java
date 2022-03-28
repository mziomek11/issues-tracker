package org.example.cqrs.event;

import java.util.Objects;

public abstract class BaseEvent {
    private final String id;
    private int version;

    protected BaseEvent(String id) {
        this.id = Objects.requireNonNull(id);
    }

    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
