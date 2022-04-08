package org.example.issuestracker.issues.command.domain.issue.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

@RequiredArgsConstructor
@Getter
public class IssueContentSetException extends IllegalStateException {
    private final transient IssueId issueId;
    private final transient IssueContent issueContent;
}
