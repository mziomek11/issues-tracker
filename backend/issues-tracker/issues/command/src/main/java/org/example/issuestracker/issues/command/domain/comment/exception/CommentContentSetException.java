package org.example.issuestracker.issues.command.domain.comment.exception;

import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;

public class CommentContentSetException extends RuntimeException {
    private final CommentId commentId;
    private final CommentContent commentContent;

    public CommentContentSetException(CommentId commentId, CommentContent commentContent) {
        this.commentId = commentId;
        this.commentContent = commentContent;
    }

    public CommentId getCommentId() {
        return commentId;
    }

    public CommentContent getCommentContent() {
        return commentContent;
    }
}
