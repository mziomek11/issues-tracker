package org.example.cqrs.event;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
