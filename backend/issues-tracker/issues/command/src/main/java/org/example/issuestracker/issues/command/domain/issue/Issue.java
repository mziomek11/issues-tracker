package org.example.issuestracker.issues.command.domain.issue;

import org.example.cqrs.domain.AggregateRoot;
import org.example.issuestracker.issues.command.domain.comment.Comment;
import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;
import org.example.issuestracker.issues.command.domain.comment.Comments;
import org.example.issuestracker.issues.command.domain.issue.exception.*;
import org.example.issuestracker.issues.command.domain.vote.Vote;
import org.example.issuestracker.issues.command.domain.vote.VoterId;
import org.example.issuestracker.issues.command.domain.vote.Votes;
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

    public Issue() {}

    public void close() {
        ensureIsOpen();
        raiseEvent(issueClosed(id));
    }

    public void rename(IssueName newName) {
        ensureIsOpen();

        if (name.equals(newName)) {
            throw new IssueNameAlreadySetException();
        }

        raiseEvent(issueRenamed(id, newName));
    }

    public void changeType(IssueType newType) {
        ensureIsOpen();

        if (type.equals(newType)) {
            throw new IssueTypeAlreadySetException();
        }

        raiseEvent(issueTypeChanged(id, newType));
    }

    public void changeContent(IssueContent newContent) {
        ensureIsOpen();

        if (content.equals(newContent)) {
            throw new IssueContentAlreadySetException();
        }

        raiseEvent(issueContentChanged(id, newContent));
    }

    public void comment(Comment comment) {
        ensureIsOpen();
        comments.ensureCanAdd(comment);

        raiseEvent(issueCommented(id, comment.getId(), comment.getContent()));
    }

    public void changeCommentContent(CommentId commentId, CommentContent commentContent) {
        ensureIsOpen();
        comments.ensureCanChangeContent(commentId, commentContent);

        raiseEvent(issueCommentContentChanged(id, commentId, commentContent));
    }

    public void hideComment(CommentId commentId) {
        ensureIsOpen();
        comments.ensureCanHide(commentId);

        raiseEvent(issueCommentHidden(id, commentId));
    }

    public void voteComment(CommentId commentId, Vote vote) {
        ensureIsOpen();
        comments.ensureCanVote(commentId, vote);

        raiseEvent(issueCommentVoted(id, commentId, vote.getVoterId(), vote.getType()));
    }

    public void vote(Vote vote) {
        ensureIsOpen();
        votes.ensureCanAdd(vote);

        raiseEvent(issueVoted(id, vote.getVoterId(), vote.getType()));
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

    public void on(IssueVotedEvent issueVotedEvent) {
        var voterId = VoterId.fromString(issueVotedEvent.getVoterId());
        var newVote = new Vote(voterId, issueVotedEvent.getVoteType());

        votes = votes.add(newVote);
    }

    @Override
    public IssueId getId() {
        return id;
    }

    private void ensureIsOpen() {
        if (isClosed()) {
            throw new IssueClosedException();
        }
    }

    private boolean isClosed() {
        return status.equals(IssueStatus.CLOSED);
    }
}
