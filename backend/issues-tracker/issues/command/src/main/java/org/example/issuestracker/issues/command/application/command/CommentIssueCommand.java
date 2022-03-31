package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

public record CommentIssueCommand(IssueId issueId, CommentContent commentContent) {
    public CommentIssueCommand(UUID issueId, String commentContent) {
        this(new IssueId(issueId), new CommentContent(commentContent));
    }
}
