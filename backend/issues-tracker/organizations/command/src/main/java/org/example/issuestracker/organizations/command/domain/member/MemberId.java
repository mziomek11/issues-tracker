package org.example.issuestracker.organizations.command.domain.member;

import org.example.cqrs.domain.EntityId;

import java.util.UUID;

public class MemberId extends EntityId {
    public MemberId(UUID id) {
        super(id);
    }
}
