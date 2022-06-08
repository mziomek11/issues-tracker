package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.OrganizationService;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.issuestracker.issues.command.application.command.OpenIssueCommand;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenIssueCommandHandler implements CommandHandler<OpenIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationService organizationService;

    /**
     * @throws OrganizationMemberNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    @Override
    public void handle(OpenIssueCommand command) {
        organizationService.ensureOrganizationHasProjectAndMember(command.getOrganizationDetails());

        var issue = Issue.open(
                command.getIssueId(),
                command.getIssueType(),
                command.getIssueContent(),
                command.getIssueName(),
                command.getOrganizationDetails()
        );

        eventSourcingHandler.save(issue);
    }
}
