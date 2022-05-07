package com.mateuszziomek.cqrs.event;

import reactor.core.publisher.Mono;

public interface ReactiveEventHandler<T extends BaseEvent> {
    Mono<Void> handle(T event);
}
