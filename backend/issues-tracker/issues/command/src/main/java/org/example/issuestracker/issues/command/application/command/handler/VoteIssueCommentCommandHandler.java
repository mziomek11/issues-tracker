package org.example.issuestracker.issues.command.application.command.handler;

import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.VoteIssueCommentCommand;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.vote.Vote;

public class VoteIssueCommentCommandHandler implements CommandHandler<VoteIssueCommentCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;

    public VoteIssueCommentCommandHandler(EventSourcingHandler<Issue> eventSourcingHandler) {
        this.eventSourcingHandler = eventSourcingHandler;
    }

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
