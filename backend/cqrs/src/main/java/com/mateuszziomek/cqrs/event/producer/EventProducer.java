package com.mateuszziomek.cqrs.event.producer;

import com.mateuszziomek.cqrs.event.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
