package org.example.issuestracker.issues.query.infrastructure.event;

import org.example.issuestracker.shared.domain.event.IssueOpenedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class IssueEventConsumer {
    Logger logger = LoggerFactory.getLogger(IssueEventConsumer.class);

    @KafkaListener(topics = "IssueOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(IssueOpenedEvent event, Acknowledgment ack) {
        logger.info("Received issue opened event");
        logger.info(event.getId().toString());
        ack.acknowledge();
    }
}
