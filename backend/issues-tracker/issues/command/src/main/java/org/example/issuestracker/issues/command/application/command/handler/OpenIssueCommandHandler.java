package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.OpenIssueCommand;
import org.example.issuestracker.issues.command.application.gateway.organization.OrganizationGateway;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenIssueCommandHandler implements CommandHandler<OpenIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationGateway organizationGateway;

    /**
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(OrganizationId, OrganizationProjectId, OrganizationMemberId)}
     */
    @Override
    public void handle(OpenIssueCommand command) {
        organizationGateway.ensureOrganizationHasProjectAndMember(
                command.getOrganizationId(),
                command.getProjectId(),
                command.getMemberId()
        );

        var issue = Issue.open(
                command.getIssueId(),
                command.getOrganizationId(),
                command.getProjectId(),
                command.getMemberId(),
                command.getIssueType(),
                command.getIssueContent(),
                command.getIssueName()
        );

        eventSourcingHandler.save(issue);
    }
}
