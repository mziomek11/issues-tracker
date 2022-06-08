package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.OrganizationService;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.issuestracker.issues.command.application.command.ChangeIssueCommentContentCommand;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentContent;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentId;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeIssueCommentContentCommandHandler implements CommandHandler<ChangeIssueCommentContentCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationService organizationService;


    /**
     * @throws CommentContentSetException see {@link Issue#changeCommentContent(CommentId, CommentContent, OrganizationMemberId)}
     * @throws CommentNotFoundException see {@link Issue#changeCommentContent(CommentId, CommentContent, OrganizationMemberId)}
     * @throws IssueClosedException see {@link Issue#changeCommentContent(CommentId, CommentContent, OrganizationMemberId)}
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws OrganizationMemberNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    @Override
    public void handle(ChangeIssueCommentContentCommand command) {
        organizationService.ensureOrganizationHasProjectAndMember(command.getOrganizationDetails());
        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.changeCommentContent(
                command.getCommentId(),
                command.getCommentContent(),
                command.getOrganizationDetails().memberId()
        );

        eventSourcingHandler.save(issue);
    }
}
