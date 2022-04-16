package org.example.cqrs.event.dispatcher;

import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventHandler;

public interface EventDispatcher {
    /**
     * @throws TooManyEventHandlersException if given event has already registered handler
     */
    <T extends BaseEvent> void registerHandler(Class<T> type, EventHandler<T> handler);
    /**
     * @throws EventHandlerNotFoundException if given event has not registered handler
     */
    <T extends BaseEvent> void dispatch(T event);
}
