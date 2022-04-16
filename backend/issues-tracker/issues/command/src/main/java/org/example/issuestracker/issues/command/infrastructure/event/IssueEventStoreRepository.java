package org.example.issuestracker.issues.command.infrastructure.event;

import org.example.cqrs.event.EventModel;
import org.example.cqrs.event.store.EventStoreRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueEventStoreRepository extends MongoRepository<EventModel, String>, EventStoreRepository {}
