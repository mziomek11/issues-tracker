package org.example.issuestracker.issues.command.domain.issue;

import org.example.cqrs.domain.AggregateRoot;
import org.example.issuestracker.issues.command.domain.comment.Comment;
import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.comment.Comments;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentWithIdExistsException;
import org.example.issuestracker.issues.command.domain.issue.exception.*;
import org.example.issuestracker.issues.command.domain.vote.Vote;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.command.domain.vote.Votes;
import org.example.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import org.example.issuestracker.issues.common.domain.issue.IssueStatus;
import org.example.issuestracker.issues.common.domain.issue.IssueType;
import org.example.issuestracker.issues.common.event.*;

import static org.example.issuestracker.issues.command.domain.EventFactory.*;

public class Issue extends AggregateRoot {
    private IssueId id;
    private IssueType type;
    private IssueStatus status;
    private IssueContent content;
    private IssueName name;
    private Comments comments = new Comments();
    private Votes votes = new Votes();

    public static Issue open(IssueId id, IssueType type, IssueContent content, IssueName name) {
        var issue = new Issue();

        issue.raiseEvent(issueOpened(id, type, content, name));

        return issue;
    }

    public Issue() {
        // Required to apply events
    }

    public void close() {
        ensureIsOpen();
        raiseEvent(issueClosed(id));
    }

    @Override
    public IssueId getId() {
        return id;
    }

    /**
     * Renames issue
     *
     * @param newName to be set
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws IssueNameSetException if given name is the same as current name
     */
    public void rename(IssueName newName) {
        ensureIsOpen();

        if (name.equals(newName)) {
            throw new IssueNameSetException(id, name);
        }

        raiseEvent(issueRenamed(id, newName));
    }

    /**
     * Changes issue type
     *
     * @param newType to be set
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws IssueTypeSetException if given type is the same as current type
     */
    public void changeType(IssueType newType) {
        ensureIsOpen();

        if (type.equals(newType)) {
            throw new IssueTypeSetException(id, type);
        }

        raiseEvent(issueTypeChanged(id, newType));
    }

    /**
     * Changes issue content
     *
     * @param newContent to be set
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws IssueContentSetException if given content is the same as current content
     */
    public void changeContent(IssueContent newContent) {
        ensureIsOpen();

        if (content.equals(newContent)) {
            throw new IssueContentSetException(id, content);
        }

        raiseEvent(issueContentChanged(id, newContent));
    }

    /**
     * Adds comment to issue
     *
     * @param comment to be added
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws CommentWithIdExistsException see {@link Comments#ensureCanAdd(Comment)}
     */
    public void comment(Comment comment) {
        ensureIsOpen();
        comments.ensureCanAdd(comment);

        raiseEvent(issueCommented(id, comment.getId(), comment.getContent()));
    }

    /**
     * Changes content of issues comment
     *
     * @param commentId of comment to be changed
     * @param commentContent to be set
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws CommentNotFoundException see {@link Comments#ensureCanChangeContent(CommentId, CommentContent)}
     * @throws CommentContentSetException see {@link Comments#ensureCanChangeContent(CommentId, CommentContent)}
     */
    public void changeCommentContent(CommentId commentId, CommentContent commentContent) {
        ensureIsOpen();
        comments.ensureCanChangeContent(commentId, commentContent);

        raiseEvent(issueCommentContentChanged(id, commentId, commentContent));
    }

    /**
     * Hides issues comment
     *
     * @param commentId of comment to be hidden
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws CommentNotFoundException see {@link Comments#ensureCanHide(CommentId)}
     * @throws CommentHiddenException see {@link Comments#ensureCanHide(CommentId)}
     */
    public void hideComment(CommentId commentId) {
        ensureIsOpen();
        comments.ensureCanHide(commentId);

        raiseEvent(issueCommentHidden(id, commentId));
    }

    /**
     * Adds vote to the comment
     *
     * @param commentId of comment to be hidden
     * @param vote to be added
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws CommentNotFoundException see {@link Comments#ensureCanVote(CommentId, Vote)}
     * @throws VoteAlreadyExistsException see {@link Comments#ensureCanVote(CommentId, Vote)}
     */
    public void voteComment(CommentId commentId, Vote vote) {
        ensureIsOpen();
        comments.ensureCanVote(commentId, vote);

        raiseEvent(issueCommentVoted(id, commentId, vote.getVoterId(), vote.getType()));
    }

    /**
     * Adds vote to the issue
     *
     * @param vote to be added
     * @throws IssueClosedException see {@link Issue#ensureIsOpen()}
     * @throws VoteAlreadyExistsException see {@link Votes#ensureCanAdd(Vote)}
     */
    public void vote(Vote vote) {
        ensureIsOpen();
        votes.ensureCanAdd(vote);

        raiseEvent(issueVoted(id, vote.getVoterId(), vote.getType()));
    }

    /**
     * Ensures that issue status is {@linkplain IssueStatus#OPENED OPENED}
     *
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

    public void on(IssueOpenedEvent issueOpenedEvent) {
        id = IssueId.fromString(issueOpenedEvent.getId());
        type = issueOpenedEvent.getIssueType();
        status = IssueStatus.OPENED;
        content = new IssueContent(issueOpenedEvent.getIssueContent());
        name = new IssueName(issueOpenedEvent.getIssueName());
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
        var commentId = CommentId.fromString(issueCommentedEvent.getCommentId());
        var commentContent = new CommentContent(issueCommentedEvent.getCommentContent());
        var comment = new Comment(commentId, commentContent);

        comments = comments.add(comment);
    }

    public void on(IssueCommentContentChangedEvent issueCommentContentChangedEvent) {
        var commentId = CommentId.fromString(issueCommentContentChangedEvent.getCommentId());
        var commentContent = new CommentContent(issueCommentContentChangedEvent.getCommentContent());

        comments = comments.changeContent(commentId, commentContent);
    }

    public void on(IssueCommentHiddenEvent issueCommentHiddenEvent) {
        var commentId = CommentId.fromString(issueCommentHiddenEvent.getCommentId());

        comments = comments.hide(commentId);
    }

    public void on(IssueCommentVotedEvent issueCommentVotedEvent) {
        var commentId = CommentId.fromString(issueCommentVotedEvent.getCommentId());
        var voterId = VoterId.fromString(issueCommentVotedEvent.getVoterId());
        var vote = new Vote(voterId, issueCommentVotedEvent.getVoteType());

        comments = comments.vote(commentId, vote);
    }

    public void on(IssueVotedEvent issueVotedEvent) {
        var voterId = VoterId.fromString(issueVotedEvent.getVoterId());
        var newVote = new Vote(voterId, issueVotedEvent.getVoteType());

        votes = votes.add(newVote);
    }
}
