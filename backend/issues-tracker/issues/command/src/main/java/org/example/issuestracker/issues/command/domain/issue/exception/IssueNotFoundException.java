package org.example.issuestracker.issues.command.domain.issue.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

@RequiredArgsConstructor
@Getter
public class IssueNotFoundException extends IllegalStateException {
    private final transient IssueId issueId;
}
