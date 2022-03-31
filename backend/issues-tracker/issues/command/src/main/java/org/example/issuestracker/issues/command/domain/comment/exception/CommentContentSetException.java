package org.example.issuestracker.issues.command.domain.comment.exception;

import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;

public class CommentContentSetException extends IllegalStateException {
    private final transient CommentId commentId;
    private final transient CommentContent commentContent;

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
