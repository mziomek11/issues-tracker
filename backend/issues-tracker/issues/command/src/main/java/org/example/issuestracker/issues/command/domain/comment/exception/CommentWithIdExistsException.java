package org.example.issuestracker.issues.command.domain.comment.exception;

import org.example.issuestracker.issues.command.domain.comment.CommentId;

public class CommentWithIdExistsException extends IllegalStateException {
    private final transient CommentId commentId;

    public CommentWithIdExistsException(CommentId commentId) {
        this.commentId = commentId;
    }

    public CommentId getCommentId() {
        return commentId;
    }
}
