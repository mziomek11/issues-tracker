package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;

import java.util.UUID;

public record ChangeIssueContentCommand(IssueId issueId, IssueContent issueContent) {
    public ChangeIssueContentCommand(UUID issueId, String text) {
        this(new IssueId(issueId), new IssueContent(text));
    }
}
