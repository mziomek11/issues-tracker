package org.example.issuestracker.users.command.infrastructure.event;

import org.example.cqrs.event.producer.EventProducer;
import org.example.cqrs.event.producer.KafkaEventProducer;
import org.example.cqrs.event.sourcinghandler.DefaultEventSourcingHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.cqrs.event.store.DefaultEventStore;
import org.example.cqrs.event.store.EventStore;
import org.example.issuestracker.users.command.domain.account.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class UserEventConfiguration {
    @Bean
    public EventProducer eventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        return new KafkaEventProducer(kafkaTemplate);
    }

    @Bean
    public EventSourcingHandler<User> eventSourcingHandler(EventStore eventStore) {
        return new DefaultEventSourcingHandler<>(eventStore, User::new);
    }

    @Bean
    public EventStore eventStore(UserEventStoreRepository userEventStoreRepository, EventProducer eventProducer) {
        return new DefaultEventStore<>(userEventStoreRepository, eventProducer, User.class);
    }
}
