package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.ChangeIssueTypeCommand;
import org.example.issuestracker.issues.command.application.gateway.organization.OrganizationGateway;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueTypeSetException;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import org.example.issuestracker.shared.domain.valueobject.IssueType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeIssueTypeCommandHandler implements CommandHandler<ChangeIssueTypeCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationGateway organizationGateway;

    /**
     * @throws IssueClosedException see {@link Issue#changeType(OrganizationId, OrganizationProjectId, OrganizationMemberId, IssueType)}
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws IssueTypeSetException see {@link Issue#changeType(OrganizationId, OrganizationProjectId, OrganizationMemberId, IssueType)}
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
     */
    @Override
    public void handle(ChangeIssueTypeCommand command) {
        organizationGateway.ensureOrganizationHasProjectAndMember(
                command.getOrganizationId(),
                command.getProjectId(),
                command.getMemberId()
        );

        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.changeType(
                command.getOrganizationId(),
                command.getProjectId(),
                command.getMemberId(),
                command.getIssueType()
        );

        eventSourcingHandler.save(issue);
    }
}
