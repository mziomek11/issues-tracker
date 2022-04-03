package org.example.issuestracker.issues.command.application.command.handler;

import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.VoteIssueCommentCommand;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.vote.Vote;
import org.example.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;

public class VoteIssueCommentCommandHandler implements CommandHandler<VoteIssueCommentCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    public VoteIssueCommentCommandHandler(EventSourcingHandler<Issue> eventSourcingHandler) {
        this.eventSourcingHandler = eventSourcingHandler;
    }

    /**
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws IssueClosedException see {@link Issue#voteComment(CommentId, Vote)}
     * @throws CommentNotFoundException see {@link Issue#voteComment(CommentId, Vote)}
     * @throws VoteAlreadyExistsException see {@link Issue#voteComment(CommentId, Vote)}
     */
    @Override
    public void handle(VoteIssueCommentCommand command) {
        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        var vote = new Vote(command.getVoterId(), command.getVoteType());
        issue.voteComment(command.getCommentId(), vote);

        eventSourcingHandler.save(issue);
    }
}
