package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

public class ChangeIssueCommentContentCommand {
    private final IssueId issueId;
    private final CommentId commendId;
    private final CommentContent commentContent;

    public ChangeIssueCommentContentCommand(UUID issueId, UUID commentId, String commentContent) {
        this.issueId = new IssueId(issueId);
        this.commendId = new CommentId(commentId);
        this.commentContent = new CommentContent(commentContent);
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public CommentId getCommentId() {
        return commendId;
    }

    public CommentContent getCommentContent() {
        return commentContent;
    }
}
