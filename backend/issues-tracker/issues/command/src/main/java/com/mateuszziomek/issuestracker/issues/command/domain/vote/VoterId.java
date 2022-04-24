package com.mateuszziomek.issuestracker.issues.command.domain.vote;

import com.mateuszziomek.cqrs.domain.EntityId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;

import java.util.UUID;

public class VoterId extends EntityId {
    public static VoterId fromMemberId(OrganizationMemberId memberId) {
        return new VoterId(memberId.getValue());
    }

    public VoterId(UUID id) {
        super(id);
    }
}
