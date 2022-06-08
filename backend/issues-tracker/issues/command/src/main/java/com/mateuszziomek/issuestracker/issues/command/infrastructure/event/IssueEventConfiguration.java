package com.mateuszziomek.issuestracker.issues.command.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.EventDispatcher;
import com.mateuszziomek.cqrs.event.store.EventStore;
import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.producer.KafkaEventProducer;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.Issue;
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
        return new EventSourcingHandler<>(eventStore, Issue::new);
    }

    @Bean
    public EventStore eventStore(IssueEventStoreRepository issueEventStoreRepository, EventProducer eventProducer) {
        return new EventStore<>(issueEventStoreRepository, eventProducer, Issue.class);
    }

    @Bean
    public EventDispatcher eventDispatcher() {
        return new EventDispatcher();
    }
}
