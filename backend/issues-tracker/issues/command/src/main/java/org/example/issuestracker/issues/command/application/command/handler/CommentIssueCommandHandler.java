package org.example.issuestracker.issues.command.application.command.handler;

import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.CommentIssueCommand;
import org.example.issuestracker.issues.command.domain.comment.Comment;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;

public class CommentIssueCommandHandler implements CommandHandler<CommentIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    public CommentIssueCommandHandler(EventSourcingHandler<Issue> eventSourcingHandler) {
        this.eventSourcingHandler = eventSourcingHandler;
    }

    @Override
    public void handle(CommentIssueCommand command) {
        var issue = eventSourcingHandler
                .getById(command.issueId())
                .orElseThrow(() -> new IssueNotFoundException(command.issueId()));

        var comment = new Comment(CommentId.generate(), command.commentContent());
        issue.comment(comment);

        eventSourcingHandler.save(issue);
    }
}
