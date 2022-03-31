package org.example.issuestracker.issues.command.application.command.handler;

import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.ChangeIssueContentCommand;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;

public class ChangeIssueContentCommandHandler implements CommandHandler<ChangeIssueContentCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    public ChangeIssueContentCommandHandler(EventSourcingHandler<Issue> eventSourcingHandler) {
        this.eventSourcingHandler = eventSourcingHandler;
    }

    @Override
    public void handle(ChangeIssueContentCommand command) {
        var issue = eventSourcingHandler
                .getById(command.issueId())
                .orElseThrow(() -> new IssueNotFoundException(command.issueId()));

        issue.changeContent(command.issueContent());

        eventSourcingHandler.save(issue);
    }
}
