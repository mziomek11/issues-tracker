package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.IssueContent;
import org.example.issuestracker.issues.command.domain.IssueId;
import org.example.issuestracker.issues.common.domain.IssueType;

import java.util.UUID;

public class OpenIssueCommand {
    private final IssueId issueId;
    private final IssueType issueType;
    private final IssueContent issueContent;

    public OpenIssueCommand(UUID issueId, IssueType issueType, String text) {
        this.issueId = new IssueId(issueId);
        this.issueType = issueType;
        this.issueContent = new IssueContent(text);
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
}
