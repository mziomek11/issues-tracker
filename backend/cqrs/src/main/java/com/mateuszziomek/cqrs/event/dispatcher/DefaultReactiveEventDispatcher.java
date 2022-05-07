package com.mateuszziomek.cqrs.event.dispatcher;

import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.EventHandler;
import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultReactiveEventDispatcher implements ReactiveEventDispatcher {
    private final Map<Class<? extends BaseEvent>, List<ReactiveEventHandler<BaseEvent>>> registry = new HashMap<>();

    /**
     * @throws TooManyEventHandlersException see {@link ReactiveEventDispatcher#registerHandler(Class, ReactiveEventHandler)}
     */
    @Override
    public <T extends BaseEvent> void registerHandler(Class<T> type, ReactiveEventHandler<T> handler) {
        var handlers = registry.computeIfAbsent(type, c -> new ArrayList<>());

        if (!handlers.isEmpty()) {
            throw new TooManyEventHandlersException();
        }

        handlers.add((ReactiveEventHandler<BaseEvent>) handler);
    }

    /**
     * @throws EventHandlerNotFoundException see {@link ReactiveEventDispatcher#dispatch(BaseEvent)}
     */
    @Override
    public <T extends BaseEvent> Mono<Void> dispatch(T event) {
        var handlers = registry.get(event.getClass());

        if (handlers == null) {
            throw new EventHandlerNotFoundException();
        }

        return handlers.get(0).handle(event);
    }
}
