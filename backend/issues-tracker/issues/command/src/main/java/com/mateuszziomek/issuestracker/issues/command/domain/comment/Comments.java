package com.mateuszziomek.issuestracker.issues.command.domain.comment;

import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentContentSetException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentHiddenException;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.exception.CommentNotFoundException;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.Vote;
import com.mateuszziomek.issuestracker.issues.command.domain.vote.exception.VoteAlreadyExistsException;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Comments {
    private final List<Comment> commentList;

    public Comments() {
        this.commentList = new ArrayList<>();
    }

    public Comments add(Comment comment) {
        var newComments = new ArrayList<>(commentList);
        newComments.add(comment);

        return new Comments(newComments);
    }

    /**
     * @throws CommentContentSetException see {@link Comments#ensureCanChangeContent(CommentId, CommentContent)}
     * @throws CommentNotFoundException see {@link Comments#ensureCanChangeContent(CommentId, CommentContent)}
     */
    public Comments changeContent(CommentId id, CommentContent content) {
        ensureCanChangeContent(id, content);

        return updateComment(id, comment -> comment.changeContent(content));
    }

    /**
     * @throws CommentContentSetException see {@link Comment#ensureCanChangeContent(CommentContent)}
     * @throws CommentNotFoundException see {@link Comments#findCommentByIdOrThrow(CommentId)}
     */
    public void ensureCanChangeContent(CommentId id, CommentContent content) {
        var comment = findCommentByIdOrThrow(id);

        comment.ensureCanChangeContent(content);
    }

    /**
     * @throws CommentHiddenException see {@link Comments#ensureCanHide(CommentId)}
     * @throws CommentNotFoundException see {@link Comments#ensureCanHide(CommentId)}
     */
    public Comments hide(CommentId id) {
        ensureCanHide(id);

        return updateComment(id, Comment::hide);
    }

    /**
     * @throws CommentHiddenException see {@link Comment#ensureCanHide()}
     * @throws CommentNotFoundException see {@link Comments#findCommentByIdOrThrow(CommentId)}
     */
    public void ensureCanHide(CommentId id) {
        var comment = findCommentByIdOrThrow(id);

        comment.ensureCanHide();
    }

    /**
     * @throws CommentNotFoundException see {@link Comments#ensureCanVote(CommentId, Vote)}
     * @throws VoteAlreadyExistsException see {@link Comments#ensureCanVote(CommentId, Vote)}
     */
    public Comments vote(CommentId id, Vote vote) {
        ensureCanVote(id, vote);

        return updateComment(id, comment -> comment.vote(vote));
    }

    /**
     * @throws CommentNotFoundException see {@link Comments#findCommentByIdOrThrow(CommentId)}
     * @throws VoteAlreadyExistsException see {@link Comment#ensureCanVote(Vote)}
     */
    public void ensureCanVote(CommentId id, Vote vote) {
        var comment = findCommentByIdOrThrow(id);

        comment.ensureCanVote(vote);
    }

    private Comments updateComment(CommentId id, UnaryOperator<Comment> mapper) {
        var oldComment = findCommentByIdOrThrow(id);
        var newComment = mapper.apply(oldComment);

        var newComments = commentList
                .stream()
                .filter(comment -> !comment.id().equals(id))
                .collect(Collectors.toList());

        newComments.add(newComment);

        return new Comments(newComments);
    }

    /**
     * @throws CommentNotFoundException if comment with given id does not exist
     */
    private Comment findCommentByIdOrThrow(CommentId id) {
        return findCommentById(id).orElseThrow(() -> new CommentNotFoundException(id));
    }

    private Optional<Comment> findCommentById(CommentId id) {
        return commentList
                .stream()
                .filter(comment -> comment.id().equals(id))
                .findFirst();
    }
}
