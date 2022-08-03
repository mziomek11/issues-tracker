package com.mateuszziomek.issuestracker.issues.query.domain.comment;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.CommentUpdateType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentUpdate<T> {
    private CommentUpdateType type;
    private LocalDateTime updatedAt;
    private T previousValue;
    private T currentValue;
}
