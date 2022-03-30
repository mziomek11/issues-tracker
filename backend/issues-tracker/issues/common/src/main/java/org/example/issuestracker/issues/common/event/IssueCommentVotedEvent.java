package org.example.issuestracker.issues.common.event;

import org.example.cqrs.event.BaseEvent;
import org.example.issuestracker.issues.common.domain.vote.VoteType;

import java.util.Objects;

public class IssueCommentVotedEvent extends BaseEvent {
    private final String commentId;
    private final String voterId;
    private final VoteType voteType;

    public IssueCommentVotedEvent(String issueId, String commentId, String voterId, VoteType voteType) {
        super(issueId);

        this.commentId = Objects.requireNonNull(commentId);
        this.voterId = Objects.requireNonNull(voterId);
        this.voteType = voteType;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getVoterId() {
        return voterId;
    }

    public VoteType getVoteType() {
        return voteType;
    }
}
