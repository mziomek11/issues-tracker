package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.RenameIssueCommand;
import org.example.issuestracker.issues.command.domain.issue.IssueName;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNameSetException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;

@RequiredArgsConstructor
public class RenameIssueCommandHandler implements CommandHandler<RenameIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    /**
     * @throws IssueClosedException see {@link Issue#rename(IssueName)}
     * @throws IssueNameSetException see {@link Issue#rename(IssueName)}
     * @throws IssueNotFoundException if issue with given id does not exist
     */
    @Override
    public void handle(RenameIssueCommand command) {
        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.rename(command.getIssueName());

        eventSourcingHandler.save(issue);
    }
}
