package com.mateuszziomek.issuestracker.issues.query.infrastructure.event;

import com.mateuszziomek.cqrs.event.BaseEvent;
import com.mateuszziomek.cqrs.event.dispatcher.ReactiveEventDispatcher;
import com.mateuszziomek.issuestracker.shared.domain.event.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueEventConsumer {
    private final ReactiveEventDispatcher eventDispatcher;

    @KafkaListener(topics = "IssueClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueClosedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment);
    }

    @KafkaListener(topics = "IssueCommentedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueCommentedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment);
    }

    @KafkaListener(topics = "IssueCommentContentChangedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueCommentContentChangedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment);
    }

    @KafkaListener(topics = "IssueCommentHiddenEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueCommentHiddenEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment);
    }

    @KafkaListener(topics = "IssueCommentVotedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueCommentVotedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment);
    }

    @KafkaListener(topics = "IssueContentChangedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueContentChangedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment);
    }

    @KafkaListener(topics = "IssueOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueOpenedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment);
    }

    @KafkaListener(topics = "IssueRenamedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueRenamedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment);
    }

    @KafkaListener(topics = "IssueTypeChangedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueTypeChangedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment);
    }

    @KafkaListener(topics = "IssueVotedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueVotedEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment);
    }

    @KafkaListener(topics = "UserRegisteredEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(UserRegisteredEvent event, Acknowledgment acknowledgment) {
        consumeEvent(event, acknowledgment);
    }

    private void consumeEvent(BaseEvent event, Acknowledgment acknowledgment) {
        eventDispatcher
                .dispatch(event)
                .subscribe(unused -> acknowledgment.acknowledge());
    }
}
