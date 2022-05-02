package com.mateuszziomek.issuestracker.notifications.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.EventDispatcher;
import com.mateuszziomek.issuestracker.shared.domain.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final EventDispatcher eventDispatcher;

    @KafkaListener(topics = "UserRegisteredEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(UserRegisteredEvent event) {
        eventDispatcher.dispatch(event);
    }
}
