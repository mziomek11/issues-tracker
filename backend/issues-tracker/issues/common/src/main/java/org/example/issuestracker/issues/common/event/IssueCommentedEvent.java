package org.example.issuestracker.issues.common.event;

import org.example.cqrs.event.BaseEvent;

import java.util.Objects;

public class IssueCommentedEvent extends BaseEvent {
    private final String commentId;
    private final String commentContent;

    public IssueCommentedEvent(String issueId, String commentId, String commentContent) {
        super(issueId);

        this.commentId = Objects.requireNonNull(commentId);
        this.commentContent = Objects.requireNonNull(commentContent);
    }

    public String getCommentId() {
        return commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }
}
