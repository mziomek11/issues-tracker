package com.mateuszziomek.cqrs.event.dispatcher;

import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.ReactiveEventHandler;
import reactor.core.publisher.Mono;

public interface ReactiveEventDispatcher {
    /**
     * @throws TooManyEventHandlersException if given event has already registered handler
     */
    <T extends BaseEvent> void registerHandler(Class<T> type, ReactiveEventHandler<T> handler);
    /**
     * @throws EventHandlerNotFoundException if given event has not registered handler
     */
    <T extends BaseEvent> Mono<Void> dispatch(T event);
}
