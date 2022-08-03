package com.mateuszziomek.issuestracker.issues.query.domain.issue;

import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueUpdateType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class IssueUpdate<T> {
    private IssueUpdateType type;
    private LocalDateTime updatedAt;
    private T previousValue;
    private T currentValue;
}
