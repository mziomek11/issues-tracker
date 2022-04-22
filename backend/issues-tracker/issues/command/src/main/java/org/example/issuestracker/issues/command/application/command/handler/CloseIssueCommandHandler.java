package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.CloseIssueCommand;
import org.example.issuestracker.issues.command.application.gateway.organization.OrganizationGateway;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CloseIssueCommandHandler implements CommandHandler<CloseIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationGateway organizationGateway;

    /**
     * @throws IssueClosedException see {@link Issue#close(IssueOrganizationDetails)}
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    @Override
    public void handle(CloseIssueCommand command) {
        organizationGateway.ensureOrganizationHasProjectAndMember(command.getOrganizationDetails());

        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.close(command.getOrganizationDetails());

        eventSourcingHandler.save(issue);
    }
}
