package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.OpenIssueCommand;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenIssueCommandHandler implements CommandHandler<OpenIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    @Override
    public void handle(OpenIssueCommand command) {
        var issue = Issue.open(
                command.getIssueId(),
                command.getIssueType(),
                command.getIssueContent(),
                command.getIssueName()
        );

        eventSourcingHandler.save(issue);
    }
}
