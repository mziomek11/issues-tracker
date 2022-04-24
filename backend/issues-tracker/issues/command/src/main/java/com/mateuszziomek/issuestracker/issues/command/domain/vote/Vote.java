package com.mateuszziomek.issuestracker.issues.command.domain.vote;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.VoteType;

public record Vote(VoterId voterId, VoteType type) {
    public boolean hasTheSameTypeAs(Vote vote) {
        return type.equals(vote.type());
    }
}
