package org.example.issuestracker.issues.command.domain.comment;

import org.example.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentWithIdExistsException;
import org.example.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import org.example.issuestracker.issues.command.domain.vote.Vote;
import org.example.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Comments {
    private final List<Comment> commentList;

    public Comments() {
        this.commentList = new ArrayList<>();
    }

    public Comments(List<Comment> commentList) {
        this.commentList = commentList;
    }

    /**
     * Adds comment to comments
     *
     * @param comment to be added
     * @throws CommentWithIdExistsException see {@link Comments#ensureCanAdd(Comment)}
     */
    public Comments add(Comment comment) {
        ensureCanAdd(comment);

        var newComments = new ArrayList<>(commentList);
        newComments.add(comment);

        return new Comments(newComments);
    }

    /**
     * Ensures that comment can be added to comments
     *
     * @param comment to be added
     * @throws CommentWithIdExistsException if comment with given id exists
     */
    public void ensureCanAdd(Comment comment) {
        var optionalExistingComment = findCommentById(comment.getId());

        if (optionalExistingComment.isPresent()) {
            throw new CommentWithIdExistsException(comment.getId());
        }
    }

    /**
     * Changes content of comment with id to the given one
     *
     * @param id of comment that should be updated
     * @param content to be set
     * @throws CommentNotFoundException see {@link Comments#ensureCanChangeContent(CommentId, CommentContent)}
     * @throws CommentContentSetException see {@link Comments#ensureCanChangeContent(CommentId, CommentContent)}
     */
    public Comments changeContent(CommentId id, CommentContent content) {
        ensureCanChangeContent(id, content);

        return updateComment(id, comment -> comment.changeContent(content));
    }

    /**
     * Ensures that comment content be changed to the given one
     *
     * @param id of comment that should be updated
     * @param content to be set
     * @throws CommentNotFoundException see {@link Comments#findCommentByIdOrThrow(CommentId)}
     * @throws CommentContentSetException see {@link Comment#ensureCanChangeContent(CommentContent)}
     */
    public void ensureCanChangeContent(CommentId id, CommentContent content) {
        var comment = findCommentByIdOrThrow(id);

        comment.ensureCanChangeContent(content);
    }

    /**
     * Hides comment with given id
     *
     * @param id of comment that should be hidden
     * @throws CommentNotFoundException see {@link Comments#ensureCanHide(CommentId)}
     * @throws CommentHiddenException see {@link Comments#ensureCanHide(CommentId)}
     */
    public Comments hide(CommentId id) {
        ensureCanHide(id);

        return updateComment(id, Comment::hide);
    }

    /**
     * Ensures that comment can be hidden
     *
     * @param id of comment that should be hidden
     * @throws CommentNotFoundException see {@link Comments#findCommentByIdOrThrow(CommentId)}
     * @throws CommentHiddenException see {@link Comment#ensureCanHide()}
     */
    public void ensureCanHide(CommentId id) {
        var comment = findCommentByIdOrThrow(id);

        comment.ensureCanHide();
    }

    /**
     * Adds vote to the comment with given id
     *
     * @param id of the comment
     * @param vote to be added
     * @throws CommentNotFoundException see {@link Comments#ensureCanVote(CommentId, Vote)}
     * @throws VoteAlreadyExistsException see {@link Comments#ensureCanVote(CommentId, Vote)}
     */
    public Comments vote(CommentId id, Vote vote) {
        ensureCanVote(id, vote);

        return updateComment(id, comment -> comment.vote(vote));
    }

    /**
     * Ensures that vote can be added to the comment with given id
     *
     * @param id of the comment
     * @param vote to be added
     * @throws CommentNotFoundException see {@link Comments#findCommentByIdOrThrow(CommentId)}
     * @throws VoteAlreadyExistsException see {@link Comment#ensureCanVote(Vote)}
     */
    public void ensureCanVote(CommentId id, Vote vote) {
        var comment = findCommentByIdOrThrow(id);

        comment.ensureCanVote(vote);
    }

    /**
     * Returns new {@linkplain Comments Comments} with applied mapper function to the
     * comment with given id
     *
     * @param id of the comment
     * @param mapper to be applied
     */
    private Comments updateComment(CommentId id, UnaryOperator<Comment> mapper) {
        var oldComment = findCommentByIdOrThrow(id);
        var newComment = mapper.apply(oldComment);

        var newComments = commentList
                .stream()
                .filter(comment -> !comment.getId().equals(id))
                .collect(Collectors.toList());

        newComments.add(newComment);

        return new Comments(newComments);
    }

    /**
     * Returns comment with given id
     *
     * @param id of the comment
     * @throws CommentNotFoundException if comment with given id does not exist
     */
    private Comment findCommentByIdOrThrow(CommentId id) {
        return findCommentById(id).orElseThrow(() -> new CommentNotFoundException(id));
    }

    private Optional<Comment> findCommentById(CommentId id) {
        return commentList
                .stream()
                .filter(comment -> comment.getId().equals(id))
                .findFirst();
    }
}
