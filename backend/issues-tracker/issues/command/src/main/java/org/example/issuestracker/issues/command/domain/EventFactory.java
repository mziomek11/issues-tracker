package org.example.issuestracker.issues.command.domain;

import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.common.domain.event.*;
import org.example.issuestracker.issues.common.domain.issue.IssueType;
import org.example.issuestracker.issues.common.domain.vote.VoteType;
import org.example.issuestracker.issues.common.event.*;

public class EventFactory {
    public static IssueOpenedEvent issueOpened(IssueId id, IssueType type, IssueContent content, IssueName name) {
        return IssueOpenedEvent
                .builder()
                .issueId(id.getValue())
                .issueType(type)
                .issueName(name.text())
                .issueContent(content.text())
                .build();
    }

    public static IssueClosedEvent issueClosed(IssueId id) {
        return IssueClosedEvent
                .builder()
                .issueId(id.getValue())
                .build();
    }

    public static IssueRenamedEvent issueRenamed(IssueId id, IssueName name) {
        return IssueRenamedEvent
                .builder()
                .issueId(id.getValue())
                .issueName(name.text())
                .build();
    }

    public static IssueTypeChangedEvent issueTypeChanged(IssueId id, IssueType type) {
        return IssueTypeChangedEvent
                .builder()
                .issueId(id.getValue())
                .issueType(type)
                .build();
    }

    public static IssueContentChangedEvent issueContentChanged(IssueId id, IssueContent content) {
        return IssueContentChangedEvent
                .builder()
                .issueId(id.getValue())
                .issueContent(content.text())
                .build();
    }

    public static IssueCommentedEvent issueCommented(
            IssueId issueId,
            CommentId commentId,
            CommentContent commentContent
    ) {
        return IssueCommentedEvent
                .builder()
                .issueId(issueId.getValue())
                .commentId(commentId.getValue())
                .commentContent(commentContent.text())
                .build();
    }

    public static IssueCommentContentChangedEvent issueCommentContentChanged(
            IssueId issueId,
            CommentId commentId,
            CommentContent commentContent
    ) {
        return IssueCommentContentChangedEvent
                .builder()
                .issueId(issueId.getValue())
                .commentId(commentId.getValue())
                .commentContent(commentContent.text())
                .build();
    }

    public static IssueCommentHiddenEvent issueCommentHidden(IssueId issueId, CommentId commentId) {
        return IssueCommentHiddenEvent
                .builder()
                .issueId(issueId.getValue())
                .commentId(commentId.getValue())
                .build();
    }

    public static IssueVotedEvent issueVoted(IssueId issueId, VoterId voterId, VoteType voteType) {
        return IssueVotedEvent
                .builder()
                .issueId(issueId.getValue())
                .voterId(voterId.getValue())
                .voteType(voteType)
                .build();
    }

    public static IssueCommentVotedEvent issueCommentVoted(
            IssueId issueId,
            CommentId commentId,
            VoterId voterId,
            VoteType voteType
    ) {
        return IssueCommentVotedEvent
                .builder()
                .issueId(issueId.getValue())
                .commentId(commentId.getValue())
                .voterId(voterId.getValue())
                .voteType(voteType)
                .build();
    }

    private EventFactory() {}
}
