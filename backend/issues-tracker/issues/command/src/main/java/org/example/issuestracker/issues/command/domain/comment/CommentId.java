package org.example.issuestracker.issues.command.domain.comment;

import org.example.cqrs.domain.EntityId;

import java.util.UUID;

public class CommentId extends EntityId {
    public static CommentId fromString(String value) {
        return new CommentId(UUID.fromString(value));
    }

    public static CommentId generate() {
        return new CommentId(UUID.randomUUID());
    }

    public CommentId(UUID id) {
        super(id);
    }
}
