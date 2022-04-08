package org.example.issuestracker.issues.command.domain.issue.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;

@RequiredArgsConstructor
@Getter
public class IssueNameSetException extends IllegalStateException {
    private final transient IssueId issueId;
    private final transient IssueName issueName;
}
