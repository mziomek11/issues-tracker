package org.example.issuestracker.accounts.query.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.event.dispatcher.EventDispatcher;
import org.example.issuestracker.shared.domain.event.AccountOpenedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventConsumer {
    private final EventDispatcher eventDispatcher;

    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(AccountOpenedEvent event, Acknowledgment ack) {
        eventDispatcher.dispatch(event);
        ack.acknowledge();
    }
}
