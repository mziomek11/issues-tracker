package com.mateuszziomek.issuestracker.issues.command.domain.issue;

import com.mateuszziomek.issuestracker.issues.command.domain.EventFactory;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentContent;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.Comments;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentWithIdExistsException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueClosedException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueContentSetException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueNameSetException;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.exception.IssueTypeSetException;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationMemberId;
import com.mateuszziomek.issuestracker.issues.command.domain.organization.OrganizationProjectId;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.Vote;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.VoterId;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.Votes;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import com.mateuszziomek.issuestracker.shared.domain.event.*;
import lombok.NoArgsConstructor;
import com.mateuszziomek.cqrs.domain.AggregateRoot;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.Comment;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentId;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueStatus;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;

@NoArgsConstructor
public class Issue extends AggregateRoot {
    private IssueId id;
    private IssueType type;
    private IssueStatus status;
    private IssueContent content;
    private IssueName name;
    private Comments comments = new Comments();
    private Votes votes = new Votes();
    private IssueOrganizationDetails organizationDetails;
    private EventFactory ef = new EventFactory(this);

    public static Issue open(
            IssueId id,
            IssueType type,
            IssueContent content,
            IssueName name,
            IssueOrganizationDetails organizationDetails
    ) {
        var issue = new Issue();

        issue.raiseEvent(EventFactory.issueOpened(id, type, content, name, organizationDetails));

        return issue;
    }

    /**
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     */
    public void close(OrganizationMemberId operatorId) {
        ensureIsOpen();
        raiseEvent(ef.issueClosed(operatorId));
    }

    /**
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws IssueNameSetException if given name is the same as current name
     */
    public void rename(IssueName newName, OrganizationMemberId operatorId) {
        ensureIsOpen();

        if (name.equals(newName)) {
            throw new IssueNameSetException(id, name);
        }

        raiseEvent(ef.issueRenamed(newName, operatorId));
    }

    /**
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws IssueTypeSetException if given type is the same as current type
     */
    public void changeType(IssueType newType, OrganizationMemberId operatorId) {
        ensureIsOpen();

        if (type.equals(newType)) {
            throw new IssueTypeSetException(id, type);
        }

        raiseEvent(ef.issueTypeChanged(newType, operatorId));
    }

    /**
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws IssueContentSetException if given content is the same as current content
     */
    public void changeContent(IssueContent newContent, OrganizationMemberId operatorId) {
        ensureIsOpen();

        if (content.equals(newContent)) {
            throw new IssueContentSetException(id, content);
        }

        raiseEvent(ef.issueContentChanged(newContent, operatorId));
    }

    /**
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws CommentWithIdExistsException see {@link Comments#ensureCanAdd(Comment)}
     */
    public void comment(Comment comment, OrganizationMemberId operatorId) {
        ensureIsOpen();
        comments.ensureCanAdd(comment);

        raiseEvent(ef.issueCommented(comment, operatorId));
    }

    /**
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws CommentNotFoundException see {@link Comments#ensureCanChangeContent(CommentId, CommentContent)}
     * @throws CommentContentSetException see {@link Comments#ensureCanChangeContent(CommentId, CommentContent)}
     */
    public void changeCommentContent(
            CommentId commentId,
            CommentContent commentContent,
            OrganizationMemberId operatorId
    ) {
        ensureIsOpen();
        comments.ensureCanChangeContent(commentId, commentContent);

        raiseEvent(ef.issueCommentContentChanged(commentId, commentContent, operatorId));
    }

    /**
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws CommentNotFoundException see {@link Comments#ensureCanHide(CommentId)}
     * @throws CommentHiddenException see {@link Comments#ensureCanHide(CommentId)}
     */
    public void hideComment(CommentId commentId, OrganizationMemberId operatorId) {
        ensureIsOpen();
        comments.ensureCanHide(commentId);

        raiseEvent(ef.issueCommentHidden(commentId, operatorId));
    }

    /**
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws CommentNotFoundException see {@link Comments#ensureCanVote(CommentId, Vote)}
     * @throws VoteAlreadyExistsException see {@link Comments#ensureCanVote(CommentId, Vote)}
     */
    public void voteComment(CommentId commentId, Vote vote) {
        ensureIsOpen();
        comments.ensureCanVote(commentId, vote);

        raiseEvent(ef.issueCommentVoted(commentId, vote));
    }

    /**
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws VoteAlreadyExistsException see {@link Votes#ensureCanAdd(Vote)}
     */
    public void vote(Vote vote) {
        ensureIsOpen();
        votes.ensureCanAdd(vote);

        raiseEvent(ef.issueVoted(vote));
    }

    /**
     * @throws IssueClosedException if issue status is not {@linkplain IssueStatus#OPENED}
     */
    private void ensureIsOpen() {
        if (isClosed()) {
            throw new IssueClosedException(id);
        }
    }

    private boolean isClosed() {
        return status.equals(IssueStatus.CLOSED);
    }

    @Override
    public IssueId getId() {
        return id;
    }

    public IssueOrganizationDetails getOrganizationDetails() {
        return organizationDetails;
    }

    public void on(IssueOpenedEvent issueOpenedEvent) {
        id = new IssueId(issueOpenedEvent.getId());
        type = issueOpenedEvent.getIssueType();
        status = IssueStatus.OPENED;
        content = new IssueContent(issueOpenedEvent.getIssueContent());
        name = new IssueName(issueOpenedEvent.getIssueName());
        organizationDetails = new IssueOrganizationDetails(
                new OrganizationId(issueOpenedEvent.getOrganizationId()),
                new OrganizationProjectId(issueOpenedEvent.getProjectId()),
                new OrganizationMemberId(issueOpenedEvent.getMemberId())
        );
    }

    public void on(IssueClosedEvent issueClosedEvent) {
        status = IssueStatus.CLOSED;
    }

    public void on(IssueRenamedEvent issueRenamedEvent) {
        name = new IssueName(issueRenamedEvent.getIssueName());
    }

    public void on(IssueTypeChangedEvent issueTypeChangedEvent) {
        type = issueTypeChangedEvent.getIssueType();
    }

    public void on(IssueContentChangedEvent issueContentChangedEvent) {
        content = new IssueContent(issueContentChangedEvent.getIssueContent());
    }

    public void on(IssueCommentedEvent issueCommentedEvent) {
        var commentId = new CommentId(issueCommentedEvent.getCommentId());
        var commentContent = new CommentContent(issueCommentedEvent.getCommentContent());
        var comment = new Comment(commentId, commentContent);

        comments = comments.add(comment);
    }

    public void on(IssueCommentContentChangedEvent issueCommentContentChangedEvent) {
        var commentId = new CommentId(issueCommentContentChangedEvent.getCommentId());
        var commentContent = new CommentContent(issueCommentContentChangedEvent.getCommentContent());

        comments = comments.changeContent(commentId, commentContent);
    }

    public void on(IssueCommentHiddenEvent issueCommentHiddenEvent) {
        var commentId = new CommentId(issueCommentHiddenEvent.getCommentId());

        comments = comments.hide(commentId);
    }

    public void on(IssueCommentVotedEvent issueCommentVotedEvent) {
        var commentId = new CommentId(issueCommentVotedEvent.getCommentId());
        var voterId = new VoterId(issueCommentVotedEvent.getMemberId());
        var vote = new Vote(voterId, issueCommentVotedEvent.getVoteType());

        comments = comments.vote(commentId, vote);
    }

    public void on(IssueVotedEvent issueVotedEvent) {
        var voterId = new VoterId(issueVotedEvent.getMemberId());
        var newVote = new Vote(voterId, issueVotedEvent.getVoteType());

        votes = votes.add(newVote);
    }
}
