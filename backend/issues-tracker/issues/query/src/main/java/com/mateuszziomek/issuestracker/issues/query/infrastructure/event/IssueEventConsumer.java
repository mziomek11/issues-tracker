package com.mateuszziomek.issuestracker.issues.query.infrastructure.event;

import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.dispatcher.ReactiveEventDispatcher;
import com.mateuszziomek.issuestracker.issues.query.infrastructure.gateway.notification.NotificationGateway;
import com.mateuszziomek.issuestracker.shared.domain.event.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IssueEventConsumer {
    private final ReactiveEventDispatcher eventDispatcher;
    private final NotificationGateway notificationGateway;

    @KafkaListener(topics = "IssueClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueClosedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "IssueCommentedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueCommentedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "IssueCommentContentChangedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueCommentContentChangedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "IssueCommentHiddenEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueCommentHiddenEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "IssueCommentVotedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueCommentVotedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "IssueContentChangedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueContentChangedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "IssueOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueOpenedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "IssueRenamedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueRenamedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "IssueTypeChangedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueTypeChangedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "IssueVotedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueVotedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, notificationGateway.notify(event));
    }

    @KafkaListener(topics = "OrganizationCreatedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrganizationCreatedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, Mono.empty());
    }

    @KafkaListener(topics = "OrganizationMemberJoinedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrganizationMemberJoinedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, Mono.empty());
    }
    
    @KafkaListener(topics = "UserRegisteredEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(UserRegisteredEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment, Mono.empty());
    }

    private void consumeEvent(BaseEvent event, Acknowledgment acknowledgment, Mono<Void> notificationMono) {
        eventDispatcher
                .dispatch(event)
                .doOnSuccess(unused -> acknowledgment.acknowledge())
                .then(notificationMono)
                .subscribe();
    }
}
