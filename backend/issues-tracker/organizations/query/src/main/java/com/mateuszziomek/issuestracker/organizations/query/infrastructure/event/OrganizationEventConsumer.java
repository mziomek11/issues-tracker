package com.mateuszziomek.issuestracker.organizations.query.infrastructure.event;

import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.event.dispatcher.EventDispatcher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationEventConsumer {
    private final EventDispatcher eventDispatcher;

    @KafkaListener(topics = "OrganizationCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrganizationCreatedEvent event) {
        eventDispatcher.dispatch(event);
    }

    @KafkaListener(topics = "OrganizationMemberJoinedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrganizationMemberJoinedEvent event) {
        eventDispatcher.dispatch(event);
    }

    @KafkaListener(topics = "OrganizationProjectCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrganizationProjectCreatedEvent event) {
        eventDispatcher.dispatch(event);
    }
}
