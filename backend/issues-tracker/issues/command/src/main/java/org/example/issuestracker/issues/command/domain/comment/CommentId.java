package org.example.issuestracker.issues.command.domain.comment;

import org.example.cqrs.domain.EntityId;

import java.util.UUID;

public class CommentId extends EntityId {
    public CommentId(UUID id) {
        super(id);
    }
}
