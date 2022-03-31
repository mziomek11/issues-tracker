package org.example.issuestracker.issues.command.domain.comment.exception;

import org.example.issuestracker.issues.command.domain.comment.CommentId;

public class CommentHiddenException extends IllegalStateException {
    private final transient CommentId commentId;

    public CommentHiddenException(CommentId commentId) {
        this.commentId = commentId;
    }

    public CommentId getCommentId() {
        return commentId;
    }
}
