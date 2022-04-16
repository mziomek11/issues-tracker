package org.example.cqrs.event.producer;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaEventProducer implements EventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
