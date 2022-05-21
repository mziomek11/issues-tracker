package com.mateuszziomek.issuestracker.issues.command.domain;

import com.mateuszziomek.issuestracker.issues.command.domain.comment.Comment;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.*;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentContent;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.Vote;
import com.mateuszziomek.issuestracker.shared.domain.event.*;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventFactory {
    private final Issue issue;

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

    public IssueClosedEvent issueClosed(OrganizationMemberId operatorId) {
        return IssueClosedEvent
                .builder()
                .issueId(issue.getId().getValue())
                .organizationId(issue.getOrganizationDetails().organizationId().getValue())
                .projectId(issue.getOrganizationDetails().projectId().getValue())
                .memberId(operatorId.getValue())
                .build();
    }

    public IssueRenamedEvent issueRenamed(IssueName name, OrganizationMemberId operatorId) {
        return IssueRenamedEvent
                .builder()
                .issueId(issue.getId().getValue())
                .organizationId(issue.getOrganizationDetails().organizationId().getValue())
                .projectId(issue.getOrganizationDetails().projectId().getValue())
                .memberId(operatorId.getValue())
                .issueName(name.text())
                .build();
    }

    public IssueTypeChangedEvent issueTypeChanged(IssueType type, OrganizationMemberId operatorId) {
        return IssueTypeChangedEvent
                .builder()
                .issueId(issue.getId().getValue())
                .organizationId(issue.getOrganizationDetails().organizationId().getValue())
                .projectId(issue.getOrganizationDetails().projectId().getValue())
                .memberId(operatorId.getValue())
                .issueType(type)
                .build();
    }

    public IssueContentChangedEvent issueContentChanged(IssueContent content, OrganizationMemberId operatorId) {
        return IssueContentChangedEvent
                .builder()
                .issueId(issue.getId().getValue())
                .organizationId(issue.getOrganizationDetails().organizationId().getValue())
                .projectId(issue.getOrganizationDetails().projectId().getValue())
                .memberId(operatorId.getValue())
                .issueContent(content.text())
                .build();
    }

    public IssueCommentedEvent issueCommented(
            Comment comment,
            OrganizationMemberId operatorId
    ) {
        return IssueCommentedEvent
                .builder()
                .issueId(issue.getId().getValue())
                .organizationId(issue.getOrganizationDetails().organizationId().getValue())
                .projectId(issue.getOrganizationDetails().projectId().getValue())
                .memberId(operatorId.getValue())
                .commentId(comment.id().getValue())
                .commentContent(comment.content().text())
                .build();
    }

    public IssueCommentContentChangedEvent issueCommentContentChanged(
            CommentId commentId,
            CommentContent commentContent,
            OrganizationMemberId operatorId
    ) {
        return IssueCommentContentChangedEvent
                .builder()
                .issueId(issue.getId().getValue())
                .organizationId(issue.getOrganizationDetails().organizationId().getValue())
                .projectId(issue.getOrganizationDetails().projectId().getValue())
                .memberId(operatorId.getValue())
                .commentId(commentId.getValue())
                .commentContent(commentContent.text())
                .build();
    }

    public IssueCommentHiddenEvent issueCommentHidden(CommentId commentId, OrganizationMemberId operatorId) {
        return IssueCommentHiddenEvent
                .builder()
                .issueId(issue.getId().getValue())
                .organizationId(issue.getOrganizationDetails().organizationId().getValue())
                .projectId(issue.getOrganizationDetails().projectId().getValue())
                .memberId(operatorId.getValue())
                .commentId(commentId.getValue())
                .build();
    }

    public IssueVotedEvent issueVoted(Vote vote) {
        return IssueVotedEvent
                .builder()
                .issueId(issue.getId().getValue())
                .organizationId(issue.getOrganizationDetails().organizationId().getValue())
                .projectId(issue.getOrganizationDetails().projectId().getValue())
                .memberId(vote.voterId().getValue())
                .voteType(vote.type())
                .build();
    }

    public IssueCommentVotedEvent issueCommentVoted(
            CommentId commentId,
            Vote vote
    ) {
        return IssueCommentVotedEvent
                .builder()
                .issueId(issue.getId().getValue())
                .organizationId(issue.getOrganizationDetails().organizationId().getValue())
                .projectId(issue.getOrganizationDetails().projectId().getValue())
                .memberId(vote.voterId().getValue())
                .commentId(commentId.getValue())
                .voteType(vote.type())
                .build();
    }
}
