package com.mateuszziomek.issuestracker.users.command.infrastructure.event;

import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.dispatcher.EventDispatcher;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventConsumer {
    private final EventDispatcher eventDispatcher;

    @KafkaListener(topics = "UserRegisteredEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(UserRegisteredEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment);
    }

    private void consumeEvent(BaseEvent event, Acknowledgment acknowledgment) {
        eventDispatcher.dispatch(event);
        acknowledgment.acknowledge();
    }
}
