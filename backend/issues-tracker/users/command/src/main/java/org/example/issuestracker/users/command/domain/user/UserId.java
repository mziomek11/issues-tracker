package org.example.issuestracker.users.command.domain.user;

import org.example.cqrs.domain.AggregateId;

import java.util.UUID;

public class UserId extends AggregateId {
    public UserId(UUID id) {
        super(id);
    }
}
