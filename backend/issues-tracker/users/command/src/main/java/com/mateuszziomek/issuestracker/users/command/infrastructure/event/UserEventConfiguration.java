package com.mateuszziomek.issuestracker.users.command.infrastructure.event;

import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.producer.KafkaEventProducer;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.cqrs.event.store.EventStore;
import com.mateuszziomek.issuestracker.users.command.domain.user.User;
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
        return new EventSourcingHandler<>(eventStore, User::new);
    }

    @Bean
    public EventStore eventStore(UserEventStoreRepository userEventStoreRepository, EventProducer eventProducer) {
        return new EventStore<>(userEventStoreRepository, eventProducer, User.class);
    }
}
