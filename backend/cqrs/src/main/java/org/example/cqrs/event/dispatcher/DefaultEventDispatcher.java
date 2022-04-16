package org.example.cqrs.event.dispatcher;

import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultEventDispatcher implements EventDispatcher {
    private final Map<Class<? extends BaseEvent>, List<EventHandler<BaseEvent>>> registry = new HashMap<>();

    /**
     * @throws TooManyEventHandlersException see {@link EventDispatcher#registerHandler(Class, EventHandler)}
     */
    @Override
    public <T extends BaseEvent> void registerHandler(Class<T> type, EventHandler<T> handler) {
        var handlers = registry.computeIfAbsent(type, c -> new ArrayList<>());

        if (handlers.size() > 1) {
            throw new TooManyEventHandlersException();
        }

        handlers.add((EventHandler<BaseEvent>) handler);
    }

    /**
     * @throws EventHandlerNotFoundException see {@link EventDispatcher#dispatch(BaseEvent)}
     */
    @Override
    public <T extends BaseEvent> void dispatch(T event) {
        var handlers = registry.get(event.getClass());

        if (handlers == null) {
            throw new EventHandlerNotFoundException();
        }

        handlers.get(0).handle(event);
    }
}
