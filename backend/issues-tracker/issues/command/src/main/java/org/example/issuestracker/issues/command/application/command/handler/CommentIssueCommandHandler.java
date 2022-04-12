package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.CommentIssueCommand;
import org.example.issuestracker.issues.command.domain.comment.Comment;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentWithIdExistsException;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentIssueCommandHandler implements CommandHandler<CommentIssueCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    /**
     * @throws CommentWithIdExistsException see {@link Issue#close()}
     * @throws IssueClosedException see {@link Issue#close()}
     * @throws IssueNotFoundException if issue with given id does not exist
     */
    @Override
    public void handle(CommentIssueCommand command) {
        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        var comment = new Comment(command.getCommentId(), command.getCommentContent());
        issue.comment(comment);

        eventSourcingHandler.save(issue);
    }
}
