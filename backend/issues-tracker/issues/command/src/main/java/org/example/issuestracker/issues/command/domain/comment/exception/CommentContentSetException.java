package org.example.issuestracker.issues.command.domain.comment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.issues.command.domain.comment.CommentContent;
import org.example.issuestracker.issues.command.domain.comment.CommentId;

@RequiredArgsConstructor
@Getter
public class CommentContentSetException extends IllegalStateException {
    private final transient CommentId commentId;
    private final transient CommentContent commentContent;
}
