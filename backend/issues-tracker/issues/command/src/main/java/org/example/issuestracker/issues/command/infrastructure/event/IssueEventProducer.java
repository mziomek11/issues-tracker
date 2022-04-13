package org.example.issuestracker.issues.command.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IssueEventProducer implements EventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
