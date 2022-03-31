package org.example.issuestracker.issues.command.domain.comment;

import org.example.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import org.example.issuestracker.issues.command.domain.vote.Vote;
import org.example.issuestracker.issues.command.domain.vote.Votes;
import org.example.issuestracker.issues.common.domain.comment.CommentStatus;

public class Comment {
    private final CommentId id;
    private final CommentContent content;
    private final CommentStatus status;
    private final Votes votes;

    public Comment(CommentId id, CommentContent content) {
        this.id = id;
        this.content = content;
        this.status = CommentStatus.ACTIVE;
        this.votes = new Votes();
    }

    private Comment(CommentId id, CommentContent content, CommentStatus status, Votes votes) {
        this.id = id;
        this.content = content;
        this.status = status;
        this.votes = votes;
    }

    public Comment hide() {
        ensureCanHide();

        return new Comment(id, content, CommentStatus.HIDDEN, votes);
    }

    public void ensureCanHide() {
        if (isHidden()) {
            throw new CommentHiddenException();
        }
    }

    public Comment changeContent(CommentContent newContent) {
        ensureCanChangeContent(newContent);

        return new Comment(id, newContent, status, votes);
    }

    public void ensureCanChangeContent(CommentContent newContent) {
        if (content.equals(newContent)) {
            throw new CommentContentSetException();
        }
    }

    public Comment vote(Vote vote) {
        ensureCanVote(vote);

        var newVotes = votes.add(vote);

        return new Comment(id, content, status, newVotes);
    }

    public void ensureCanVote(Vote vote) {
        votes.ensureCanAdd(vote);
    }

    public CommentId getId() {
        return id;
    }

    public CommentContent getContent() {
        return content;
    }

    private boolean isHidden() {
        return status.equals(CommentStatus.HIDDEN);
    }
}
