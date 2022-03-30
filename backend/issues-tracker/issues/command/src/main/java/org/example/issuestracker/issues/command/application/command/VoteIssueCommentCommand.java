package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.common.domain.vote.VoteType;

import java.util.UUID;

public class VoteIssueCommentCommand {
    private final IssueId issueId;
    private final CommentId commentId;
    private final VoterId voterId;
    private final VoteType voteType;

    public VoteIssueCommentCommand(UUID issueId, UUID commentId, UUID voterId, VoteType voteType) {
        this.issueId = new IssueId(issueId);
        this.commentId = new CommentId(commentId);
        this.voterId = new VoterId(voterId);
        this.voteType = voteType;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public CommentId getCommentId() {
        return commentId;
    }

    public VoterId getVoterId() {
        return voterId;
    }

    public VoteType getVoteType() {
        return voteType;
    }
}
