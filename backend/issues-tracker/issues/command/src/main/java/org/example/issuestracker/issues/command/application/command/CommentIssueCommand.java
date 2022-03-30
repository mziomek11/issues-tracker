package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

public class CommentIssueCommand {
    private final IssueId issueId;
    private final CommentContent commentContent;

    public CommentIssueCommand(UUID issueId, String commentContent) {
        this.issueId = new IssueId(issueId);
        this.commentContent = new CommentContent(commentContent);
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public CommentContent getCommentContent() {
        return commentContent;
    }
}
