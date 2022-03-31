package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.common.domain.vote.VoteType;

import java.util.UUID;

public record VoteIssueCommentCommand(IssueId issueId, CommentId commentId, VoterId voterId, VoteType voteType) {
    public VoteIssueCommentCommand(UUID issueId, UUID commentId, UUID voterId, VoteType voteType) {
        this(new IssueId(issueId), new CommentId(commentId), new VoterId(voterId), voteType);
    }
}
