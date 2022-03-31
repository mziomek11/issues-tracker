package org.example.issuestracker.issues.command.domain.vote;

import org.example.issuestracker.issues.common.domain.vote.VoteType;

public record Vote(VoterId voterId, VoteType type) {
    /**
     * Checks if votes have the same types
     *
     * @param vote to be compared
     */
    public boolean hasTheSameTypeAs(Vote vote) {
        return type.equals(vote.type());
    }
}
