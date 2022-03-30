package org.example.issuestracker.issues.command.domain.comment;

import org.example.issuestracker.issues.command.domain.comment.exception.CommentWithIdAlreadyExistsException;
import org.example.issuestracker.issues.command.domain.issue.exception.CommentNotFoundException;
import org.example.issuestracker.issues.command.domain.vote.Vote;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Comments {
    private final List<Comment> comments;

    public Comments() {
        this.comments = new ArrayList<>();
    }

    public Comments(List<Comment> comments) {
        this.comments = comments;
    }

    public Comments add(Comment comment) {
        ensureCanAdd(comment);

        var newComments = new ArrayList<>(comments);
        newComments.add(comment);

        return new Comments(newComments);
    }

    public void ensureCanAdd(Comment comment) {
        var optionalExistingComment = findCommentById(comment.getId());

        if (optionalExistingComment.isPresent()) {
            throw new CommentWithIdAlreadyExistsException(comment.getId());
        }
    }

    public Comments changeContent(CommentId id, CommentContent content) {
        ensureCanChangeContent(id, content);

        return updateComment(id, comment -> comment.changeContent(content));
    }

    public void ensureCanChangeContent(CommentId commentId, CommentContent commentContent) {
        var comment = findCommentByIdOrThrow(commentId);

        comment.ensureCanChangeContent(commentContent);
    }

    public Comments hide(CommentId id) {
        ensureCanHide(id);

        return updateComment(id, comment -> comment.hide());
    }

    public void ensureCanHide(CommentId id) {
        var comment = findCommentByIdOrThrow(id);

        comment.ensureCanHide();
    }

    public Comments vote(CommentId id, Vote vote) {
        ensureCanVote(id, vote);

        return updateComment(id, comment -> comment.vote(vote));
    }

    public void ensureCanVote(CommentId id, Vote vote) {
        var comment = findCommentByIdOrThrow(id);

        comment.ensureCanVote(vote);
    }

    private Comments updateComment(CommentId id, Function<Comment, Comment> mapper) {
        var oldComment = findCommentByIdOrThrow(id);
        var newComment = mapper.apply(oldComment);

        var newComments = comments
                .stream()
                .filter(comment -> !comment.getId().equals(id))
                .collect(Collectors.toList());

        newComments.add(newComment);

        return new Comments(newComments);
    }

    private Comment findCommentByIdOrThrow(CommentId commentId) {
        return findCommentById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    private Optional<Comment> findCommentById(CommentId commentId) {
        return comments
                .stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst();
    }
}
