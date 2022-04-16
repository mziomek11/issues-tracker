package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.ChangeIssueCommentContentCommand;
import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangeIssueCommentContentCommandHandler implements CommandHandler<ChangeIssueCommentContentCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    /**
     * @throws CommentContentSetException see {@link Issue#changeCommentContent(CommentId, CommentContent)}
     * @throws CommentNotFoundException see {@link Issue#changeCommentContent(CommentId, CommentContent)}
     * @throws IssueClosedException see {@link Issue#changeCommentContent(CommentId, CommentContent)}
     * @throws IssueNotFoundException if issue with given id does not exist
     */
    @Override
    public void handle(ChangeIssueCommentContentCommand command) {
        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        issue.changeCommentContent(command.getCommentId(), command.getCommentContent());

        eventSourcingHandler.save(issue);
    }
}
