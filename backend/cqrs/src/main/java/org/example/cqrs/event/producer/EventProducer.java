package org.example.cqrs.event.producer;

import org.example.cqrs.event.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
