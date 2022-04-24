package com.mateuszziomek.issuestracker.issues.command.domain.comment;

import com.mateuszziomek.cqrs.domain.EntityId;

import java.util.UUID;

public class CommentId extends EntityId {
    public CommentId(UUID id) {
        super(id);
    }
}
