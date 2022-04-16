package org.example.issuestracker.accounts.command.infrastructure.event;

import org.example.cqrs.event.producer.EventProducer;
import org.example.cqrs.event.producer.KafkaEventProducer;
import org.example.cqrs.event.sourcinghandler.DefaultEventSourcingHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.cqrs.event.store.DefaultEventStore;
import org.example.cqrs.event.store.EventStore;
import org.example.issuestracker.accounts.command.domain.account.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class AccountEventConfiguration {
    @Bean
    public EventProducer eventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        return new KafkaEventProducer(kafkaTemplate);
    }

    @Bean
    public EventSourcingHandler<Account> eventSourcingHandler(EventStore eventStore) {
        return new DefaultEventSourcingHandler<>(eventStore, Account::new);
    }

    @Bean
    public EventStore eventStore(AccountEventStoreRepository accountEventStoreRepository, EventProducer eventProducer) {
        return new DefaultEventStore<>(accountEventStoreRepository, eventProducer, Account.class);
    }
}
