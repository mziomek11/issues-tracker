package org.example.issuestracker.issues.command.domain.comment;

import org.example.issuestracker.issues.command.domain.comment.exception.CommentAlreadyHiddenException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentContentAlreadySetException;
import org.example.issuestracker.issues.common.domain.comment.CommentStatus;

public class Comment {
    private final CommentId id;
    private final CommentContent content;
    private final CommentStatus status;

    public Comment(CommentId id, CommentContent content) {
        this.id = id;
        this.content = content;
        this.status = CommentStatus.ACTIVE;
    }

    private Comment(CommentId id, CommentContent content, CommentStatus status) {
        this.id = id;
        this.content = content;
        this.status = status;
    }

    public Comment hide() {
        ensureCanHide();

        return new Comment(id, content, CommentStatus.HIDDEN);
    }

    public void ensureCanHide() {
        if (isHidden()) {
            throw new CommentAlreadyHiddenException();
        }
    }

    public Comment changeContent(CommentContent newContent) {
        ensureCanChangeContent(newContent);

        return new Comment(id, newContent, status);
    }

    public void ensureCanChangeContent(CommentContent newContent) {
        if (content.equals(newContent)) {
            throw new CommentContentAlreadySetException();
        }
    }

    public CommentId getId() {
        return id;
    }

    public CommentContent getContent() {
        return content;
    }

    private boolean isHidden() {
        return status.equals(CommentStatus.HIDDEN);
    }
}
