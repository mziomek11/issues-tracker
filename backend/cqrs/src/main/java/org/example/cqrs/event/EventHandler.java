package org.example.cqrs.event;

public interface EventHandler<T extends BaseEvent> {
    void handle(T event);
}
