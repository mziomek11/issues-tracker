package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.HideIssueCommentCommand;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HideIssueCommentCommandHandler implements CommandHandler<HideIssueCommentCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    /**
     * @throws CommentHiddenException see {@link Issue#hideComment(CommentId)}
     * @throws CommentNotFoundException see {@link Issue#hideComment(CommentId)}
     * @throws IssueClosedException see {@link Issue#hideComment(CommentId)}
     * @throws IssueNotFoundException if issue with given id does not exist
     */
    @Override
    public void handle(HideIssueCommentCommand command) {
        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.hideComment(command.getCommentId());

        eventSourcingHandler.save(issue);
    }
}
