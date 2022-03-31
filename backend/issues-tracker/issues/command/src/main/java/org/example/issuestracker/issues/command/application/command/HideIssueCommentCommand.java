package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

public record HideIssueCommentCommand(IssueId issueId, CommentId commentId) {
    public HideIssueCommentCommand(UUID issueId, UUID commentId) {
        this(new IssueId(issueId), new CommentId(commentId));
    }
}
