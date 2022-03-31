package org.example.issuestracker.issues.command.application.command.handler;

import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.OpenIssueCommand;
import org.example.issuestracker.issues.command.domain.issue.Issue;

public class OpenIssueCommandHandler implements CommandHandler<OpenIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    public OpenIssueCommandHandler(EventSourcingHandler<Issue> eventSourcingHandler) {
        this.eventSourcingHandler = eventSourcingHandler;
    }

    @Override
    public void handle(OpenIssueCommand command) {
        var issue = Issue.open(
                command.issueId(),
                command.issueType(),
                command.issueContent(),
                command.issueName()
        );

        eventSourcingHandler.save(issue);
    }
}
