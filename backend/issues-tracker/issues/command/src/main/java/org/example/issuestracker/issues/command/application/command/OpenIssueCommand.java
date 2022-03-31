package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;
import org.example.issuestracker.issues.common.domain.issue.IssueType;

import java.util.UUID;

public record OpenIssueCommand(IssueId issueId, IssueType issueType, IssueContent issueContent, IssueName issueName) {
    public OpenIssueCommand(UUID issueId, IssueType issueType, String text, String issueName) {
        this(new IssueId(issueId), issueType, new IssueContent(text), new IssueName(issueName));
    }
}
