package com.mateuszziomek.issuestracker.issues.command.domain.comment.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentContent;
import com.mateuszziomek.issuestracker.issues.command.domain.comment.CommentId;

@RequiredArgsConstructor
@Getter
public class CommentContentSetException extends IllegalStateException {
    private final transient CommentId commentId;
    private final transient CommentContent commentContent;
}
