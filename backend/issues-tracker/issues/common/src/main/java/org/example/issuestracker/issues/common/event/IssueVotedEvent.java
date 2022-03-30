package org.example.issuestracker.issues.common.event;

import org.example.cqrs.event.BaseEvent;
import org.example.issuestracker.issues.common.domain.vote.VoteType;

import java.util.Objects;

public class IssueVotedEvent extends BaseEvent {
    private final String voterId;
    private final VoteType voteType;

    public IssueVotedEvent(String issueId, String voterId, VoteType voteType) {
        super(issueId);

        this.voterId = Objects.requireNonNull(voterId);
        this.voteType = voteType;
    }

    public String getVoterId() {
        return voterId;
    }

    public VoteType getVoteType() {
        return voteType;
    }
}
