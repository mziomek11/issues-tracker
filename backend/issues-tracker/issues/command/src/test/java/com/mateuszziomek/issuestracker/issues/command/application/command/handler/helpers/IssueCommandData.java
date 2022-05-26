package com.mateuszziomek.issuestracker.issues.command.application.command.handler.helpers;

import com.mateuszziomek.cqrs.event.EventModel;
import com.mateuszziomek.issuestracker.issues.command.application.command.*;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueId;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueCommentedEvent;
import com.mateuszziomek.issuestracker.shared.domain.event.IssueOpenedEvent;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.VoteType;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class IssueCommandData {
    public final static UUID ORGANIZATION_UUID = UUID.randomUUID();
    public final static UUID PROJECT_UUID = UUID.randomUUID();
    public final static UUID MEMBER_UUID = UUID.randomUUID();
    public final static UUID ISSUE_UUID = UUID.randomUUID();
    public final static UUID COMMENT_UUID = UUID.randomUUID();
    public final static OrganizationId ORGANIZATION_ID = new OrganizationId(ORGANIZATION_UUID);
    public final static OrganizationProjectId PROJECT_ID = new OrganizationProjectId(PROJECT_UUID);
    public final static OrganizationMemberId MEMBER_ID = new OrganizationMemberId(MEMBER_UUID);
    public final static IssueId ISSUE_ID = new IssueId(ISSUE_UUID);

    public final static IssueOrganizationDetails ORGANIZATION_DETAILS = new IssueOrganizationDetails(
            ORGANIZATION_ID,
            PROJECT_ID,
            MEMBER_ID
    );

    public final static OpenIssueCommand OPEN_ISSUE_COMMAND = OpenIssueCommand
            .builder()
            .issueId(ISSUE_UUID)
            .issueContent("Example content")
            .issueName("example name")
            .issueType(IssueType.ENHANCEMENT)
            .organizationDetails(ORGANIZATION_DETAILS)
            .build();

    public final static CloseIssueCommand CLOSE_ISSUE_COMMAND = CloseIssueCommand
            .builder()
            .issueId(ISSUE_UUID)
            .organizationDetails(ORGANIZATION_DETAILS)
            .build();

    public final static ChangeIssueContentCommand CHANGE_ISSUE_CONTENT_COMMAND = ChangeIssueContentCommand
            .builder()
            .issueId(ISSUE_UUID)
            .organizationDetails(ORGANIZATION_DETAILS)
            .issueContent("Changed issue content")
            .build();

    public final static ChangeIssueTypeCommand CHANGE_ISSUE_TYPE_COMMAND = ChangeIssueTypeCommand
            .builder()
            .issueId(ISSUE_UUID)
            .organizationDetails(ORGANIZATION_DETAILS)
            .issueType(IssueType.BUG)
            .build();

    public final static RenameIssueCommand RENAME_ISSUE_COMMAND = RenameIssueCommand
            .builder()
            .issueId(ISSUE_UUID)
            .organizationDetails(ORGANIZATION_DETAILS)
            .issueName("Changed name")
            .build();

    public final static VoteIssueCommand VOTE_ISSUE_COMMAND = VoteIssueCommand
            .builder()
            .issueId(ISSUE_UUID)
            .organizationDetails(ORGANIZATION_DETAILS)
            .voteType(VoteType.UP)
            .build();

    public final static CommentIssueCommand COMMENT_ISSUE_COMMAND = CommentIssueCommand
            .builder()
            .issueId(ISSUE_UUID)
            .organizationDetails(ORGANIZATION_DETAILS)
            .commentId(COMMENT_UUID)
            .commentContent("Example comment content")
            .build();

    public final static ChangeIssueCommentContentCommand CHANGE_ISSUE_COMMENT_CONTENT_COMMAND = ChangeIssueCommentContentCommand
            .builder()
            .issueId(ISSUE_UUID)
            .organizationDetails(ORGANIZATION_DETAILS)
            .commentId(COMMENT_UUID)
            .commentContent("Changed comment content")
            .build();

    public final static HideIssueCommentCommand HIDE_ISSUE_COMMENT_COMMAND = HideIssueCommentCommand
            .builder()
            .issueId(ISSUE_UUID)
            .organizationDetails(ORGANIZATION_DETAILS)
            .commentId(COMMENT_UUID)
            .build();

    public final static VoteIssueCommentCommand VOTE_ISSUE_COMMENT_COMMAND = VoteIssueCommentCommand
            .builder()
            .issueId(ISSUE_UUID)
            .organizationDetails(ORGANIZATION_DETAILS)
            .commentId(COMMENT_UUID)
            .voteType(VoteType.UP)
            .build();

    public final static IssueOpenedEvent ISSUE_OPENED_EVENT = IssueOpenedEvent
            .builder()
            .issueId(ISSUE_UUID)
            .issueContent(OPEN_ISSUE_COMMAND.getIssueContent().text())
            .issueName(OPEN_ISSUE_COMMAND.getIssueName().text())
            .issueType(IssueType.ENHANCEMENT)
            .memberId(MEMBER_UUID)
            .organizationId(ORGANIZATION_UUID)
            .projectId(PROJECT_UUID)
            .build();

    public final static EventModel ISSUE_OPENED_EVENT_MODEL = new EventModel(
            ISSUE_UUID,
            Date.from(Instant.now()),
            ISSUE_ID,
            IssueOpenedEvent.class.getTypeName(),
            0,
            "IssueOpenedEvent",
            ISSUE_OPENED_EVENT
    );

    public static final IssueCommentedEvent ISSUE_COMMENTED_EVENT = IssueCommentedEvent
            .builder()
            .issueId(ISSUE_UUID)
            .commentContent(COMMENT_ISSUE_COMMAND.getCommentContent().text())
            .commentId(COMMENT_UUID)
            .memberId(MEMBER_UUID)
            .organizationId(ORGANIZATION_UUID)
            .projectId(PROJECT_UUID)
            .build();

    public final static EventModel ISSUE_COMMENTED_EVENT_MODEL = new EventModel(
            ISSUE_UUID,
            Date.from(Instant.now()),
            ISSUE_ID,
            IssueCommentedEvent.class.getTypeName(),
            0,
            "IssueCommentedEvent",
            ISSUE_COMMENTED_EVENT
    );
}
