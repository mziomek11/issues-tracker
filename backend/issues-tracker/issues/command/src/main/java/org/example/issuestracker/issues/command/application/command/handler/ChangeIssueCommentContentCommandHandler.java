package org.example.issuestracker.issues.command.application.command.handler;

import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.ChangeIssueCommentContentCommand;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;

public class ChangeIssueCommentContentCommandHandler implements CommandHandler<ChangeIssueCommentContentCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    public ChangeIssueCommentContentCommandHandler(EventSourcingHandler<Issue> eventSourcingHandler) {
        this.eventSourcingHandler = eventSourcingHandler;
    }

    @Override
    public void handle(ChangeIssueCommentContentCommand command) {
        var issue = eventSourcingHandler
                .getById(command.issueId())
                .orElseThrow(() -> new IssueNotFoundException(command.issueId()));

        issue.changeCommentContent(command.commentId(), command.commentContent());

        eventSourcingHandler.save(issue);
    }
}
