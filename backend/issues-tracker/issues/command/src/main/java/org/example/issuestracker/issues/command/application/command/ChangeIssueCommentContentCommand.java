package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

public record ChangeIssueCommentContentCommand(IssueId issueId, CommentId commentId, CommentContent commentContent) {
    public ChangeIssueCommentContentCommand(UUID issueId, UUID commentId, String commentContent) {
        this(new IssueId(issueId), new CommentId(commentId), new CommentContent(commentContent));
    }
}
