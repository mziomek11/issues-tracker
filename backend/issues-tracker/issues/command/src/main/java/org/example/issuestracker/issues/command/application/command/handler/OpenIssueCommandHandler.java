package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.OpenIssueCommand;
import org.example.issuestracker.issues.command.application.command.gateway.organization.OrganizationGateway;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.IssueCreatorIsNotMemberOfProjectException;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.issues.command.application.command.gateway.organization.exception.ProjectNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.IssueCreatorId;
import org.example.issuestracker.issues.command.domain.organization.OrganizationId;
import org.example.issuestracker.issues.command.domain.project.ProjectId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenIssueCommandHandler implements CommandHandler<OpenIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationGateway organizationGateway;

    /**
     * @throws IssueCreatorIsNotMemberOfProjectException see {@link OrganizationGateway#ensureIssueCreatorIsMemberOfProject(OrganizationId, ProjectId, IssueCreatorId)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureIssueCreatorIsMemberOfProject(OrganizationId, ProjectId, IssueCreatorId)}
     * @throws ProjectNotFoundException see {@link OrganizationGateway#ensureIssueCreatorIsMemberOfProject(OrganizationId, ProjectId, IssueCreatorId)}
     */
    @Override
    public void handle(OpenIssueCommand command) {
        organizationGateway.ensureIssueCreatorIsMemberOfProject(
                command.getOrganizationId(),
                command.getProjectId(),
                command.getIssueCreatorId()
        );

        var issue = Issue.open(
                command.getIssueId(),
                command.getOrganizationId(),
                command.getProjectId(),
                command.getIssueCreatorId(),
                command.getIssueType(),
                command.getIssueContent(),
                command.getIssueName()
        );

        eventSourcingHandler.save(issue);
    }
}
