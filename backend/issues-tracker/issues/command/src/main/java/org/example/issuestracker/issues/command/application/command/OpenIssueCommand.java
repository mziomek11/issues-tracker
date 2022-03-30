package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.issue.IssueContent;
import org.example.issuestracker.issues.command.domain.issue.IssueId;
import org.example.issuestracker.issues.command.domain.issue.IssueName;
import org.example.issuestracker.issues.common.domain.IssueType;

import java.util.UUID;

public class OpenIssueCommand {
    private final IssueId issueId;
    private final IssueType issueType;
    private final IssueContent issueContent;
    private final IssueName issueName;

    public OpenIssueCommand(UUID issueId, IssueType issueType, String text, String issueName) {
        this.issueId = new IssueId(issueId);
        this.issueType = issueType;
        this.issueContent = new IssueContent(text);
        this.issueName = new IssueName(issueName);
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public IssueType getIssueType() {
        return issueType;
    }

    public IssueContent getIssueContent() {
        return  issueContent;
    }

    public IssueName getIssueName() {
        return issueName;
    }
}
