package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.issues.command.application.command.HideIssueCommentCommand;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.OrganizationGateway;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentId;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HideIssueCommentCommandHandler implements CommandHandler<HideIssueCommentCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationGateway organizationGateway;

    /**
     * @throws CommentHiddenException see {@link Issue#hideComment(CommentId, IssueOrganizationDetails)}
     * @throws CommentNotFoundException see {@link Issue#hideComment(CommentId, IssueOrganizationDetails)}
     * @throws IssueClosedException see {@link Issue#hideComment(CommentId, IssueOrganizationDetails)}
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    @Override
    public void handle(HideIssueCommentCommand command) {
        organizationGateway.ensureOrganizationHasProjectAndMember(command.getOrganizationDetails());

        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.hideComment(command.getCommentId(), command.getOrganizationDetails());

        eventSourcingHandler.save(issue);
    }
}
