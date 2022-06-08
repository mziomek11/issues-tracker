package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.OrganizationService;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.issuestracker.issues.command.application.command.RenameIssueCommand;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueName;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueNameSetException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.Issue;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RenameIssueCommandHandler implements CommandHandler<RenameIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationService organizationService;

    /**
     * @throws IssueClosedException see {@link Issue#rename(IssueName, OrganizationMemberId)}
     * @throws IssueNameSetException see {@link Issue#rename(IssueName, OrganizationMemberId)}
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws OrganizationMemberNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    @Override
    public void handle(RenameIssueCommand command) {
        organizationService.ensureOrganizationHasProjectAndMember(command.getOrganizationDetails());

        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.rename(
                command.getIssueName(),
                command.getOrganizationDetails().memberId()
        );

        eventSourcingHandler.save(issue);
    }
}
