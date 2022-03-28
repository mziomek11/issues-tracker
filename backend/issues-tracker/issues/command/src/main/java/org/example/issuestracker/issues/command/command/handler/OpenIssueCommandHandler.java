package org.example.issuestracker.issues.command.command.handler;

import org.example.cqrs.command.CommandHandler;
import org.example.issuestracker.issues.command.command.OpenIssueCommand;
import org.example.issuestracker.issues.command.domain.Issue;

public class OpenIssueCommandHandler implements CommandHandler<OpenIssueCommand> {
    @Override
    public void handle(OpenIssueCommand command) {
        var issue = new Issue(command.getIssueId(), command.getIssueType(), command.getIssueContent());

        System.out.println(issue.getId().toString());
    }
}
