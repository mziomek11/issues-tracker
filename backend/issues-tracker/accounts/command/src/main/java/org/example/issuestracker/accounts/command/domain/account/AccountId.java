package org.example.issuestracker.accounts.command.domain.account;

import org.example.cqrs.domain.AggregateId;

import java.util.UUID;

public class AccountId extends AggregateId {
    public static AccountId fromString(String value) {
        return new AccountId(UUID.fromString(value));
    }

    public AccountId(UUID id) {
        super(id);
    }
}
