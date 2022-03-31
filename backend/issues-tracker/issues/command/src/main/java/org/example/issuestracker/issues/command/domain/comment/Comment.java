package org.example.issuestracker.issues.command.domain.comment;

import org.example.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import org.example.issuestracker.issues.command.domain.vote.Vote;
import org.example.issuestracker.issues.command.domain.vote.Votes;
import org.example.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import org.example.issuestracker.issues.common.domain.comment.CommentStatus;

public record Comment(CommentId id, CommentContent content, CommentStatus status, Votes votes) {
    public Comment(CommentId id, CommentContent content) {
        this(id, content, CommentStatus.ACTIVE, new Votes());
    }

    /**
     * Changes comment status to {@linkplain CommentStatus#HIDDEN HIDDEN}
     *
     * @throws CommentHiddenException if comment is already hidden
     */
    public Comment hide() {
        ensureCanHide();

        return new Comment(id, content, CommentStatus.HIDDEN, votes);
    }

    /**
     * Ensures that vote can be hidden
     *
     * @throws CommentHiddenException if comment is already hidden
     */
    public void ensureCanHide() {
        if (isHidden()) {
            throw new CommentHiddenException(id);
        }
    }

    /**
     * Changes comment content
     *
     * @param newContent to be set
     * @throws CommentContentSetException if comment already has given content
     */
    public Comment changeContent(CommentContent newContent) {
        ensureCanChangeContent(newContent);

        return new Comment(id, newContent, status, votes);
    }

    /**
     * Ensures that comment content can be changed to the given one
     *
     * @param newContent to be set
     * @throws CommentContentSetException if comment already has given content
     */
    public void ensureCanChangeContent(CommentContent newContent) {
        if (content.equals(newContent)) {
            throw new CommentContentSetException(id, newContent);
        }
    }

    /**
     * Adds vote to the comment
     *
     * @param vote to be added
     * @throws VoteAlreadyExistsException if vote with given voter id and type already exists
     */
    public Comment vote(Vote vote) {
        ensureCanVote(vote);

        var newVotes = votes.add(vote);

        return new Comment(id, content, status, newVotes);
    }

    /**
     * Ensures that vote can be added to comment
     *
     * @param vote to be added
     * @throws VoteAlreadyExistsException if vote with given voter id and type already exists
     */
    public void ensureCanVote(Vote vote) {
        votes.ensureCanAdd(vote);
    }

    private boolean isHidden() {
        return status.equals(CommentStatus.HIDDEN);
    }
}
