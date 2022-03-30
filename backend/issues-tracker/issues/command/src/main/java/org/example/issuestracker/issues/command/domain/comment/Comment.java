package org.example.issuestracker.issues.command.domain.comment;

import org.example.issuestracker.issues.command.domain.comment.exception.CommentContentAlreadySetException;

public class Comment {
    private final CommentId id;
    private CommentContent content;

    public Comment(CommentId id, CommentContent content) {
        this.id = id;
        this.content = content;
    }

    public void ensureCanChangeContentTo(CommentContent newContent) {
        if (!content.equals(newContent)) {
            throw new CommentContentAlreadySetException();
        }
    }

    public void changeContent(CommentContent newContent) {
        ensureCanChangeContentTo(newContent);
        this.content = content;
    }

    public CommentId getId() {
        return id;
    }

    public CommentContent getContent() {
        return content;
    }
}
