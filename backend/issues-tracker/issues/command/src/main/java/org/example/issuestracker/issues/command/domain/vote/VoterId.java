package org.example.issuestracker.issues.command.domain.vote;

import org.example.cqrs.domain.EntityId;

import java.util.UUID;

public class VoterId extends EntityId {
    public static VoterId fromString(String value) {
        return new VoterId(UUID.fromString(value));
    }

    public VoterId(UUID id) {
        super(id);
    }
}
