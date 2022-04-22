package org.example.issuestracker.issues.command.application.command.handler;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.event.sourcinghandler.EventSourcingHandler;
import org.example.issuestracker.issues.command.application.command.VoteIssueCommentCommand;
import org.example.issuestracker.issues.command.application.gateway.organization.OrganizationGateway;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationMemberNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationNotFoundException;
import org.example.issuestracker.issues.command.application.gateway.organization.exception.OrganizationProjectNotFoundException;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import org.example.issuestracker.issues.command.domain.issue.Issue;
import org.example.issuestracker.issues.command.domain.issue.IssueOrganizationDetails;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import org.example.issuestracker.issues.command.domain.issue.exception.IssueNotFoundException;
import org.example.issuestracker.issues.command.domain.vote.Vote;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteIssueCommentCommandHandler implements CommandHandler<VoteIssueCommentCommand> {
    private final EventSourcingHandler<Issue> eventSourcingHandler;
    private final OrganizationGateway organizationGateway;

    /**
     * @throws CommentNotFoundException see {@link Issue#voteComment(CommentId, Vote, IssueOrganizationDetails)}
     * @throws IssueClosedException see {@link Issue#voteComment(CommentId, Vote, IssueOrganizationDetails)}
     * @throws IssueNotFoundException if issue with given id does not exist
     * @throws OrganizationMemberNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws OrganizationProjectNotFoundException see {@link OrganizationGateway#ensureOrganizationHasProjectAndMember(IssueOrganizationDetails)}
     * @throws VoteAlreadyExistsException see {@link Issue#voteComment(CommentId, Vote, IssueOrganizationDetails)}
     */
    @Override
    public void handle(VoteIssueCommentCommand command) {
        organizationGateway.ensureOrganizationHasProjectAndMember(command.getOrganizationDetails());

        var issue = eventSourcingHandler
                .getById(command.getIssueId())
                .orElseThrow(() -> new IssueNotFoundException(command.getIssueId()));

        var vote = new Vote(VoterId.fromMemberId(command.getOrganizationDetails().memberId()), command.getVoteType());
        issue.voteComment(command.getCommentId(), vote, command.getOrganizationDetails());

        eventSourcingHandler.save(issue);
    }
}
