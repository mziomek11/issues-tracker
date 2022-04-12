package org.example.issuestracker.issues.command.infrastructure.event;

import org.example.cqrs.domain.AggregateId;
import org.example.cqrs.event.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueEventStoreRepository extends MongoRepository<EventModel, String> {
    List<EventModel> findByAggregateId(AggregateId aggregateId);
}
