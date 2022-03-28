package org.example.issuestracker.issues.command;

import org.example.issuestracker.issues.command.application.command.OpenIssueCommand;
import org.example.issuestracker.issues.command.domain.Issue;
import org.example.issuestracker.issues.common.domain.IssueType;

import java.util.UUID;

class IssuesCommandApplication {
    public static void main(String[] args) {
        var command = new OpenIssueCommand(UUID.randomUUID(), IssueType.BUG, "Example text");
        var issue = new Issue(command.getIssueId(), command.getIssueType(), command.getIssueContent());

        System.out.println(issue.getId().toString());
    }
}
