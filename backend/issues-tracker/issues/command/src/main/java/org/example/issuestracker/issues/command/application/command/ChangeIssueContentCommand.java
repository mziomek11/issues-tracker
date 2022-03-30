package org.example.issuestracker.issues.command.application.command;

import org.example.issuestracker.issues.command.domain.IssueContent;
import org.example.issuestracker.issues.command.domain.IssueId;

import java.util.UUID;

public class ChangeIssueContentCommand {
    private final IssueId issueId;
    private final IssueContent issueContent;

    public ChangeIssueContentCommand(UUID issueId, String text) {
        this.issueId = new IssueId(issueId);
        this.issueContent = new IssueContent(text);
    }

    public IssueId getIssueId() {
        return issueId;
    }

    public IssueContent getIssueContent() {
        return  issueContent;
    }
}
