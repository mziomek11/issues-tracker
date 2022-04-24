package com.mateuszziomek.issuestracker.organizations.command.infrastructure.event;

import com.mateuszziomek.cqrs.event.EventModel;
import com.mateuszziomek.cqrs.event.store.EventStoreRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationEventStoreRepository extends MongoRepository<EventModel, String>, EventStoreRepository {}
