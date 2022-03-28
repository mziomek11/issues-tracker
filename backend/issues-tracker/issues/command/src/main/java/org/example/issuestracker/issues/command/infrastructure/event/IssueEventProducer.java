package org.example.issuestracker.issues.command.infrastructure.event;

import org.example.cqrs.event.BaseEvent;
import org.example.cqrs.event.EventProducer;

public class IssueEventProducer implements EventProducer {
    @Override
    public void produce(String topic, BaseEvent event) {
        // @TODO Produce event
    }
}
