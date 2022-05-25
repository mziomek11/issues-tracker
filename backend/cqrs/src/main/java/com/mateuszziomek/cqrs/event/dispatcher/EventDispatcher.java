package com.mateuszziomek.cqrs.event.dispatcher;

import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher {
    private final Map<Class<? extends BaseEvent>, List<EventHandler<BaseEvent>>> registry = new HashMap<>();

    /**
     * @throws TooManyEventHandlersException if given event has already registered handler
     */
    public <T extends BaseEvent> void registerHandler(Class<T> type, EventHandler<T> handler) {
        var handlers = registry.computeIfAbsent(type, c -> new ArrayList<>());

        if (!handlers.isEmpty()) {
            throw new TooManyEventHandlersException();
        }

        handlers.add((EventHandler<BaseEvent>) handler);
    }

    /**
     * @throws EventHandlerNotFoundException if given event has not registered handler
     */
    public <T extends BaseEvent> void dispatch(T event) {
        var handlers = registry.get(event.getClass());

        if (handlers == null) {
            throw new EventHandlerNotFoundException();
        }

        handlers.get(0).handle(event);
    }
}
