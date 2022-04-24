package com.mateuszziomek.issuestracker.users.command.domain.user;

import com.mateuszziomek.cqrs.domain.AggregateId;

import java.util.UUID;

public class UserId extends AggregateId {
    public UserId(UUID id) {
        super(id);
    }
}
