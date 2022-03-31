package org.example.issuestracker.issues.command.domain.vote;

import org.example.issuestracker.issues.common.domain.vote.VoteType;

public class Vote {
    private final VoteType type;
    private final VoterId voterId;

    public Vote(VoterId voterId, VoteType type) {
        this.voterId = voterId;
        this.type = type;
    }

    /**
     * Checks if votes have the same types
     *
     * @param vote to be compared
     */
    public boolean hasTheSameTypeAs(Vote vote) {
        return type.equals(vote.getType());
    }

    public VoterId getVoterId() {
        return voterId;
    }

    public VoteType getType() {
        return type;
    }
}
