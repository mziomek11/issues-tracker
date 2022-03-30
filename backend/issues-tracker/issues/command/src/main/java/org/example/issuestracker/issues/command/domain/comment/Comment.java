package org.example.issuestracker.issues.command.domain.comment;

import org.example.issuestracker.issues.command.domain.comment.exception.CommentAlreadyHiddenException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentContentAlreadySetException;
import org.example.issuestracker.issues.common.domain.comment.CommentStatus;

public class Comment {
    private final CommentId id;
    private CommentContent content;
    private CommentStatus status = CommentStatus.ACTIVE;

    public Comment(CommentId id, CommentContent content) {
        this.id = id;
        this.content = content;
    }

    public void hide() {
        ensureCanHide();
        this.status = CommentStatus.HIDDEN;
    }

    public void ensureCanHide() {
        if (isHidden()) {
            throw new CommentAlreadyHiddenException();
        }
    }

    public void changeContent(CommentContent newContent) {
        ensureCanChangeContentTo(newContent);
        this.content = content;
    }

    public void ensureCanChangeContentTo(CommentContent newContent) {
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
