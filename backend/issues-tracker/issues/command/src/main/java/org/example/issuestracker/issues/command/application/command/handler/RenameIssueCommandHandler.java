package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.RenameIssueCommand;
import org.example.issuestracker.issues.command.application.gateway.organization.OrganizationGateway;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.IssueName;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNameSetException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RenameIssueCommandHandler implements CommandHandler<RenameIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationGateway organizationGateway;

    /**
     * @throws IssueClosedException see {@link Issue#rename(OrganizationId, OrganizationProjectId, OrganizationMemberId, IssueName)}
     * @throws IssueNameSetException see {@link Issue#rename(OrganizationId, OrganizationProjectId, OrganizationMemberId, IssueName)}
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
     */
    @Override
    public void handle(RenameIssueCommand command) {
        organizationGateway.ensureOrganizationHasProjectAndMember(
                command.getOrganizationId(),
                command.getOrganizationProjectId(),
                command.getOrganizationMemberId()
        );

        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.rename(
                command.getOrganizationId(),
                command.getOrganizationProjectId(),
                command.getOrganizationMemberId(),
                command.getIssueName()
        );

        eventSourcingHandler.save(issue);
    }
}
