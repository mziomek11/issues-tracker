package org.example.issuestracker.users.command.domain.account;

import org.example.cqrs.domain.AggregateId;

import java.util.UUID;

public class UserId extends AggregateId {
    public static UserId fromString(String value) {
        return new UserId(UUID.fromString(value));
    }

    public UserId(UUID id) {
        super(id);
    }
}
