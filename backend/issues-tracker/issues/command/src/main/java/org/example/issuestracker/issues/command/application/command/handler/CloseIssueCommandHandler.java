package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.CloseIssueCommand;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;

@RequiredArgsConstructor
public class CloseIssueCommandHandler implements CommandHandler<CloseIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    /**
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws IssueClosedException see {@link Issue#close()}
     */
    @Override
    public void handle(CloseIssueCommand command) {
        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.close();

        eventSourcingHandler.save(issue);
    }
}
