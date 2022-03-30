package org.example.issuestracker.issues.common.event;

import org.example.cqrs.event.BaseEvent;

import java.util.Objects;

public class IssueCommentHiddenEvent extends BaseEvent {
    private final String commentId;

    public IssueCommentHiddenEvent(String issueId, String commentId) {
        super(issueId);

        this.commentId = Objects.requireNonNull(commentId);
    }

    public String getCommentId() {
        return commentId;
    }
}
