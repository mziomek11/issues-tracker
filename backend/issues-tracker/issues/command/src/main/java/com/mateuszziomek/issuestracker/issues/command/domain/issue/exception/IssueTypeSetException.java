package com.mateuszziomek.issuestracker.issues.command.domain.issue.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.mateuszziomek.issuestracker.issues.command.domain.issue.IssueId;
import com.mateuszziomek.issuestracker.shared.domain.valueobject.IssueType;

@RequiredArgsConstructor
@Getter
public class IssueTypeSetException extends IllegalStateException {
    private final transient IssueId issueId;
    private final transient IssueType issueType;
}
