package org.example.issuestracker.users.query.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.event.dispatcher.EventDispatcher;
import org.example.issuestracker.shared.domain.event.UserActivatedEvent;
import org.example.issuestracker.shared.domain.event.UserRegisteredEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventConsumer {
    private final EventDispatcher eventDispatcher;

    @KafkaListener(topics = "UserRegisteredEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(UserRegisteredEvent event, Acknowledgment ack) {
        eventDispatcher.dispatch(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "UserActivatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(UserActivatedEvent event, Acknowledgment ack) {
        eventDispatcher.dispatch(event);
        ack.acknowledge();
    }
}
