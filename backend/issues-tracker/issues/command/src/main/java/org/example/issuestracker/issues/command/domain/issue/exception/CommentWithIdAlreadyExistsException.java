package org.example.issuestracker.issues.command.domain.issue.exception;

import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

public class CommentWithIdAlreadyExistsException extends RuntimeException {
    private IssueId issueId;
    private CommentId commentId;

    public CommentWithIdAlreadyExistsException(IssueId issueId, CommentId commentId) {
        this.issueId = issueId;
        this.commentId = commentId;
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public CommentId getCommentId() {
        return commentId;
    }
}
