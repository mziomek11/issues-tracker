package org.example.issuestracker.issues.command.domain.comment;

public class Comment {
    private final CommentId id;
    private final CommentContent content;

    public Comment(CommentId id, CommentContent content) {
        this.id = id;
        this.content = content;
    }

    public CommentId getId() {
        return id;
    }

    public CommentContent getContent() {
        return content;
    }
}
