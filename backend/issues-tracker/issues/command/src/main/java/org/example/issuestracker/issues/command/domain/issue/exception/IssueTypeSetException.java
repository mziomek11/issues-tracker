package org.example.issuestracker.issues.command.domain.issue.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.shared.domain.valueobject.IssueType;

@RequiredArgsConstructor
@Getter
public class IssueTypeSetException extends IllegalStateException {
    private final transient IssueId issueId;
    private final transient IssueType issueType;
}
