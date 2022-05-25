package com.mateuszziomek.issuestracker.users.query.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.EventDispatcher;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.shared.domain.event.UserActivatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventConsumer {
    private final EventDispatcher eventDispatcher;

    @KafkaListener(topics = "UserRegisteredEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(UserRegisteredEvent event) {
        eventDispatcher.dispatch(event);
    }

    @KafkaListener(topics = "UserActivatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(UserActivatedEvent event) {
        eventDispatcher.dispatch(event);
    }
}
