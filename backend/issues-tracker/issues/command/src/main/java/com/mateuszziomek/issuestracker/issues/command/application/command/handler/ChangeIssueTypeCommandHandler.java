package com.mateuszziomek.issuestracker.issues.command.application.command.handler;

import com.mateuszziomek.cqrs.event.sourcinghandler.EventSourcingHandler;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.OrganizationService;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationMemberNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.application.service.organization.exception.OrganizationProjectNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.cqrs.command.CommandHandler;
import com.mateuszziomek.issuestracker.issues.command.application.command.ChangeIssueTypeCommand;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.Issue;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueTypeSetException;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeIssueTypeCommandHandler implements CommandHandler<ChangeIssueTypeCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationService organizationService;

    /**
     * @throws IssueClosedException see {@link Issue#changeType(IssueType, OrganizationMemberId)}
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws IssueTypeSetException see {@link Issue#changeType(IssueType, OrganizationMemberId)}
     * @throws OrganizationMemberNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationNotFoundException see {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationProjectNotFoundException {@link OrganizationService#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     */
    @Override
    public void handle(ChangeIssueTypeCommand command) {
        organizationService.ensureOrganizationHasProjectAndMember(command.getOrganizationDetails());

        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.changeType(command.getIssueType(), command.getOrganizationDetails().memberId());

        eventSourcingHandler.save(issue);
    }
}
