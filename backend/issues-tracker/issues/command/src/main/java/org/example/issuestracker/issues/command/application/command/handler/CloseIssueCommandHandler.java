package org.example.issuestracker.issues.command.application.command.handler;

import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.CloseIssueCommand;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;

public class CloseIssueCommandHandler implements CommandHandler<CloseIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    public CloseIssueCommandHandler(EventSourcingHandler<Issue> eventSourcingHandler) {
        this.eventSourcingHandler = eventSourcingHandler;
    }

    @Override
    public void handle(CloseIssueCommand command) {
        var issue = eventSourcingHandler
                .getById(command.issueId())
                .orElseThrow(() -> new IssueNotFoundException(command.issueId()));

        issue.close();

        eventSourcingHandler.save(issue);
    }
}
