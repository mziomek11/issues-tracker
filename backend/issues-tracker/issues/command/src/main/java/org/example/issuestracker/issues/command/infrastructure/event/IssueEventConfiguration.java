package org.example.issuestracker.issues.command.infrastructure.event;

import org.example.cqrs.event.store.DefaultEventStore;
import org.example.cqrs.event.store.EventStore;
import org.example.cqrs.event.producer.EventProducer;
import org.example.cqrs.event.producer.KafkaEventProducer;
import org.example.cqrs.event.sourcinghandler.DefaultEventSourcingHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class IssueEventConfiguration {
    @Bean
    public EventProducer eventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        return new KafkaEventProducer(kafkaTemplate);
    }

    @Bean
    public EventSourcingHandler<Issue> eventSourcingHandler(EventStore eventStore) {
        return new DefaultEventSourcingHandler<>(eventStore, Issue::new);
    }

    @Bean
    public EventStore eventStore(IssueEventStoreRepository issueEventStoreRepository, EventProducer eventProducer) {
        return new DefaultEventStore<>(issueEventStoreRepository, eventProducer, Issue.class);
    }
}
