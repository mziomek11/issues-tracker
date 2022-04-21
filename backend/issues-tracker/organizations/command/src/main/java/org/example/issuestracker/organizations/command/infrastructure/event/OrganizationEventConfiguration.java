package org.example.issuestracker.organizations.command.infrastructure.event;

import org.example.cqrs.event.producer.EventProducer;
import org.example.cqrs.event.producer.KafkaEventProducer;
import org.example.cqrs.event.sourcinghandler.DefaultEventSourcingHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.cqrs.event.store.DefaultEventStore;
import org.example.cqrs.event.store.EventStore;
import org.example.issuestracker.organizations.command.domain.organization.Organization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class OrganizationEventConfiguration {
    @Bean
    public EventProducer eventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        return new KafkaEventProducer(kafkaTemplate);
    }

    @Bean
    public EventSourcingHandler<Organization> eventSourcingHandler(EventStore eventStore) {
        return new DefaultEventSourcingHandler<>(eventStore, Organization::new);
    }

    @Bean
    public EventStore eventStore(
            OrganizationEventStoreRepository organizationEventStoreRepository,
            EventProducer eventProducer
    ) {
        return new DefaultEventStore<>(organizationEventStoreRepository, eventProducer, Organization.class);
    }
}
