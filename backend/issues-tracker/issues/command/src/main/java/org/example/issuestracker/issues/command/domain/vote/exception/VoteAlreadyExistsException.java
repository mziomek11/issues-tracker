package org.example.issuestracker.issues.command.domain.vote.exception;

import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.common.domain.vote.VoteType;

public class VoteAlreadyExistsException extends IllegalStateException {
    private final transient VoterId voterId;
    private final transient VoteType voteType;

    public VoteAlreadyExistsException(VoterId voterId, VoteType voteType) {
        this.voterId = voterId;
        this.voteType = voteType;
    }

    public VoterId getVoterId() {
        return voterId;
    }

    public VoteType getVoteType() {
        return voteType;
    }
}
