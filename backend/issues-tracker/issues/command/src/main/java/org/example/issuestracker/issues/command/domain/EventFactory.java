package org.example.issuestracker.issues.command.domain;

import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;
import org.example.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.shared.domain.event.*;
import org.example.issuestracker.shared.domain.valueobject.IssueType;
import org.example.issuestracker.shared.domain.valueobject.VoteType;

public class EventFactory {
    public static IssueOpenedEvent issueOpened(
            IssueId id,
            IssueType type,
            IssueContent content,
            IssueName name,
            IssueOrganizationDetails organizationDetails
    ) {
        return IssueOpenedEvent
                .builder()
                .issueId(id.getValue())
                .organizationId(organizationDetails.organizationId().getValue())
                .projectId(organizationDetails.projectId().getValue())
                .memberId(organizationDetails.memberId().getValue())
                .issueType(type)
                .issueName(name.text())
                .issueContent(content.text())
                .build();
    }

    public static IssueClosedEvent issueClosed(
            IssueId id,
            IssueOrganizationDetails organizationDetails
    ) {
        return IssueClosedEvent
                .builder()
                .issueId(id.getValue())
                .organizationId(organizationDetails.organizationId().getValue())
                .projectId(organizationDetails.projectId().getValue())
                .memberId(organizationDetails.memberId().getValue())
                .build();
    }

    public static IssueRenamedEvent issueRenamed(
            IssueId id,
            IssueName name,
            IssueOrganizationDetails organizationDetails
    ) {
        return IssueRenamedEvent
                .builder()
                .issueId(id.getValue())
                .organizationId(organizationDetails.organizationId().getValue())
                .projectId(organizationDetails.projectId().getValue())
                .memberId(organizationDetails.memberId().getValue())
                .issueName(name.text())
                .build();
    }

    public static IssueTypeChangedEvent issueTypeChanged(
            IssueId id,
            IssueType type,
            IssueOrganizationDetails organizationDetails

    ) {
        return IssueTypeChangedEvent
                .builder()
                .issueId(id.getValue())
                .organizationId(organizationDetails.organizationId().getValue())
                .projectId(organizationDetails.projectId().getValue())
                .memberId(organizationDetails.memberId().getValue())
                .issueType(type)
                .build();
    }

    public static IssueContentChangedEvent issueContentChanged(
            IssueId id,
            IssueContent content,
            IssueOrganizationDetails organizationDetails
    ) {
        return IssueContentChangedEvent
                .builder()
                .issueId(id.getValue())
                .organizationId(organizationDetails.organizationId().getValue())
                .projectId(organizationDetails.projectId().getValue())
                .memberId(organizationDetails.memberId().getValue())
                .issueContent(content.text())
                .build();
    }

    public static IssueCommentedEvent issueCommented(
            IssueId issueId,
            CommentId commentId,
            CommentContent commentContent,
            IssueOrganizationDetails organizationDetails

    ) {
        return IssueCommentedEvent
                .builder()
                .issueId(issueId.getValue())
                .organizationId(organizationDetails.organizationId().getValue())
                .projectId(organizationDetails.projectId().getValue())
                .memberId(organizationDetails.memberId().getValue())
                .commentId(commentId.getValue())
                .commentContent(commentContent.text())
                .build();
    }

    public static IssueCommentContentChangedEvent issueCommentContentChanged(
            IssueId issueId,
            CommentId commentId,
            CommentContent commentContent,
            IssueOrganizationDetails organizationDetails
    ) {
        return IssueCommentContentChangedEvent
                .builder()
                .issueId(issueId.getValue())
                .organizationId(organizationDetails.organizationId().getValue())
                .projectId(organizationDetails.projectId().getValue())
                .memberId(organizationDetails.memberId().getValue())
                .commentId(commentId.getValue())
                .commentContent(commentContent.text())
                .build();
    }

    public static IssueCommentHiddenEvent issueCommentHidden(
            IssueId issueId,
            CommentId commentId,
            IssueOrganizationDetails organizationDetails

    ) {
        return IssueCommentHiddenEvent
                .builder()
                .issueId(issueId.getValue())
                .organizationId(organizationDetails.organizationId().getValue())
                .projectId(organizationDetails.projectId().getValue())
                .memberId(organizationDetails.memberId().getValue())
                .commentId(commentId.getValue())
                .build();
    }

    public static IssueVotedEvent issueVoted(
            IssueId issueId,
            VoteType voteType,
            IssueOrganizationDetails organizationDetails
    ) {
        return IssueVotedEvent
                .builder()
                .issueId(issueId.getValue())
                .organizationId(organizationDetails.organizationId().getValue())
                .projectId(organizationDetails.projectId().getValue())
                .memberId(organizationDetails.memberId().getValue())
                .voteType(voteType)
                .build();
    }

    public static IssueCommentVotedEvent issueCommentVoted(
            IssueId issueId,
            CommentId commentId,
            VoteType voteType,
            IssueOrganizationDetails organizationDetails
    ) {
        return IssueCommentVotedEvent
                .builder()
                .issueId(issueId.getValue())
                .organizationId(organizationDetails.organizationId().getValue())
                .projectId(organizationDetails.projectId().getValue())
                .memberId(organizationDetails.memberId().getValue())
                .commentId(commentId.getValue())
                .voteType(voteType)
                .build();
    }

    private EventFactory() {}
}
