package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.ChangeIssueTypeCommand;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueTypeSetException;
import org.example.issuestracker.shared.domain.valueobject.IssueType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeIssueTypeCommandHandler implements CommandHandler<ChangeIssueTypeCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    /**
     * @throws IssueClosedException see {@link Issue#changeType(IssueType)}
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws IssueTypeSetException see {@link Issue#changeType(IssueType)}
     */
    @Override
    public void handle(ChangeIssueTypeCommand command) {
        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.changeType(command.getIssueType());

        eventSourcingHandler.save(issue);
    }
}
