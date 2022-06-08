package com.mateuszziomek.issuestracker.organizations.command.infrastructure.event;

import com.mateuszziomek.cqrs.event.dispatcher.EventDispatcher;
import com.mateuszziomek.cqrs.event.producer.EventProducer;
import com.mateuszziomek.cqrs.event.producer.KafkaEventProducer;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.cqrs.event.store.EventStore;
import com.mateuszziomek.issuestracker.organizations.command.domain.organization.Organization;
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
        return new EventSourcingHandler<>(eventStore, Organization::new);
    }

    @Bean
    public EventStore eventStore(
            OrganizationEventStoreRepository organizationEventStoreRepository,
            EventProducer eventProducer
    ) {
        return new EventStore<>(organizationEventStoreRepository, eventProducer, Organization.class);
    }

    @Bean
    public EventDispatcher eventDispatcher() {
        return new EventDispatcher();
    }
}
