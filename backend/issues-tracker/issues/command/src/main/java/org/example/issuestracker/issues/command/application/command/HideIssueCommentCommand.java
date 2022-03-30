package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

public class HideIssueCommentCommand {
    private final IssueId issueId;
    private final CommentId commentId;

    public HideIssueCommentCommand(UUID issueId, UUID commentId) {
        this.issueId = new IssueId(issueId);
        this.commentId = new CommentId(commentId);
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public CommentId getCommentId() {
        return commentId;
    }
}
