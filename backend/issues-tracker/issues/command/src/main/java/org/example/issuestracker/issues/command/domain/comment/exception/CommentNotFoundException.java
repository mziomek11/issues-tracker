package org.example.issuestracker.issues.command.domain.comment.exception;

import org.example.issuestracker.issues.command.domain.comment.CommentId;

public class CommentNotFoundException extends IllegalStateException {
    private final CommentId commentId;

    public CommentNotFoundException(CommentId commentId) {
        this.commentId = commentId;
    }

    public CommentId getCommentId() {
        return commentId;
    }
}
