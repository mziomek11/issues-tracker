package com.mateuszziomek.issuestracker.organizations.command.domain.member;

import com.mateuszziomek.cqrs.domain.EntityId;

import java.util.UUID;

public class MemberId extends EntityId {
    public MemberId(UUID id) {
        super(id);
    }
}
