package org.example.issuestracker.issues.command.domain.vote;

import org.example.issuestracker.shared.domain.valueobject.VoteType;

public record Vote(VoterId voterId, VoteType type) {
    public boolean hasTheSameTypeAs(Vote vote) {
        return type.equals(vote.type());
    }
}
