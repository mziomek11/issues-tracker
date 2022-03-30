package org.example.issuestracker.issues.command.domain.comment.exception;

import org.example.issuestracker.issues.command.domain.comment.CommentId;

public class CommentWithIdAlreadyExistsException extends RuntimeException {
    private CommentId commentId;

    public CommentWithIdAlreadyExistsException(CommentId commentId) {
        this.commentId = commentId;
    }

    public CommentId getCommentId() {
        return commentId;
    }
}
