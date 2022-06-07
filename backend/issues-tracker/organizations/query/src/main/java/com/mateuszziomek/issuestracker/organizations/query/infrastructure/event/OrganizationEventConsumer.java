package com.mateuszziomek.issuestracker.organizations.query.infrastructure.event;

import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.dispatcher.ReactiveEventDispatcher;
import com.mateuszziomek.issuestracker.organizations.query.infrastructure.gateway.notification.NotificationGateway;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationCreatedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberInvitedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationMemberJoinedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.OrganizationProjectCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrganizationEventConsumer {
    private final ReactiveEventDispatcher eventDispatcher;
    private final NotificationGateway notificationGateway;

    @KafkaListener(topics = "OrganizationCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrganizationCreatedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "OrganizationMemberInvitedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrganizationMemberInvitedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "OrganizationMemberJoinedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrganizationMemberJoinedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "OrganizationProjectCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrganizationProjectCreatedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    private void consumeEvent(BaseEvent event, Acknowledgment acknowledgment, Mono<Void> notifyMono) {
        eventDispatcher
                .dispatch(event)
                .doOnSuccess(unused -> acknowledgment.acknowledge())
                .then(notifyMono)
                .subscribe();
    }
}
