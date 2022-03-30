package org.example.issuestracker.issues.command.domain;

import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.common.domain.issue.IssueType;
import org.example.issuestracker.issues.common.domain.vote.VoteType;
import org.example.issuestracker.issues.common.event.*;

public class EventFactory {
    public static IssueOpenedEvent issueOpened(IssueId id, IssueType type, IssueContent content, IssueName name) {
        return new IssueOpenedEvent(
                id.toString(),
                type,
                content.getText(),
                name.getText()
        );
    }

    public static IssueClosedEvent issueClosed(IssueId id) {
        return new IssueClosedEvent(id.toString());
    }

    public static IssueRenamedEvent issueRenamed(IssueId id, IssueName name) {
        return new IssueRenamedEvent(id.toString(), name.getText());
    }

    public static IssueTypeChangedEvent issueTypeChanged(IssueId id, IssueType type) {
        return new IssueTypeChangedEvent(id.toString(), type);
    }

    public static IssueContentChangedEvent issueContentChanged(IssueId id, IssueContent content) {
        return new IssueContentChangedEvent(id.toString(), content.getText());
    }

    public static IssueCommentedEvent issueCommented(
            IssueId issueId,
            CommentId commentId,
            CommentContent commentContent
    ) {
        return new IssueCommentedEvent(
                issueId.toString(),
                commentId.toString(),
                commentContent.getText()
        );
    }

    public static IssueCommentContentChangedEvent issueCommentContentChanged(
            IssueId issueId,
            CommentId commentId,
            CommentContent commentContent
    ) {
        return new IssueCommentContentChangedEvent(issueId.toString(), commentId.toString(), commentContent.getText());
    }

    public static IssueCommentHiddenEvent issueCommentHidden(IssueId issueId, CommentId commentId) {
        return new IssueCommentHiddenEvent(issueId.toString(), commentId.toString());
    }

    public static IssueVotedEvent issueVoted(IssueId issueId, VoterId voterId, VoteType voteType) {
        return new IssueVotedEvent(issueId.toString(), voterId.toString(), voteType);
    }

    public static IssueCommentVotedEvent issueCommentVoted(
            IssueId issueId,
            CommentId commentId,
            VoterId voterId,
            VoteType voteType
    ) {
        return new IssueCommentVotedEvent(issueId.toString(), commentId.toString(), voterId.toString(), voteType);
    }

    private EventFactory() {}
}
