package org.example.issuestracker.issues.command.domain.comment;

import org.example.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import org.example.issuestracker.issues.command.domain.vote.Vote;
import org.example.issuestracker.issues.command.domain.vote.Votes;
import org.example.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import org.example.issuestracker.shared.domain.valueobject.CommentStatus;

public record Comment(CommentId id, CommentContent content, CommentStatus status, Votes votes) {
    public Comment(CommentId id, CommentContent content) {
        this(id, content, CommentStatus.ACTIVE, new Votes());
    }

    /**
     * @throws CommentHiddenException if comment is already hidden
     */
    public Comment hide() {
        ensureCanHide();

        return new Comment(id, content, CommentStatus.HIDDEN, votes);
    }

    /**
     * @throws CommentHiddenException if comment is already hidden
     */
    public void ensureCanHide() {
        if (isHidden()) {
            throw new CommentHiddenException(id);
        }
    }

    /**
     * @throws CommentContentSetException if comment already has given content
     */
    public Comment changeContent(CommentContent newContent) {
        ensureCanChangeContent(newContent);

        return new Comment(id, newContent, status, votes);
    }

    /**
     * @throws CommentContentSetException if comment already has given content
     */
    public void ensureCanChangeContent(CommentContent newContent) {
        if (content.equals(newContent)) {
            throw new CommentContentSetException(id, newContent);
        }
    }

    /**
     * @throws VoteAlreadyExistsException if vote with given voter id and type already exists
     */
    public Comment vote(Vote vote) {
        ensureCanVote(vote);

        var newVotes = votes.add(vote);

        return new Comment(id, content, status, newVotes);
    }

    /**
     * @throws VoteAlreadyExistsException if vote with given voter id and type already exists
     */
    public void ensureCanVote(Vote vote) {
        votes.ensureCanAdd(vote);
    }

    private boolean isHidden() {
        return status.equals(CommentStatus.HIDDEN);
    }
}
