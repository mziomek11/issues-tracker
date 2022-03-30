package org.example.issuestracker.issues.command.domain.vote;

import org.example.issuestracker.issues.common.domain.vote.VoteType;

public class Vote {
    private final VoteType type;
    private final VoterId voterId;

    public Vote(VoterId voterId, VoteType type) {
        this.voterId = voterId;
        this.type = type;
    }

    public boolean hasTheSameTypeAs(Vote anotherVote) {
        return type.equals(anotherVote.getType());
    }

    public VoterId getVoterId() {
        return voterId;
    }

    public VoteType getType() {
        return type;
    }
}
