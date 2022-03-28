package org.example.issuestracker.issues.command.infrastructure.command;

import org.example.cqrs.command.CommandDispatcher;
import org.example.cqrs.command.CommandGateway;
import org.example.issuestracker.issues.command.application.command.CloseIssueCommand;
import org.example.issuestracker.issues.command.application.command.OpenIssueCommand;
import org.example.issuestracker.issues.command.application.command.handler.CloseIssueCommandHandler;
import org.example.issuestracker.issues.command.application.command.handler.OpenIssueCommandHandler;

public class IssueCommandGateway implements CommandGateway {
    private final CommandDispatcher commandDispatcher;

    public IssueCommandGateway(
            CommandDispatcher commandDispatcher,
            OpenIssueCommandHandler openIssueCommandHandler,
            CloseIssueCommandHandler closeIssueCommandHandler
    ) {
        this.commandDispatcher = commandDispatcher;
        this.commandDispatcher.registerHandler(OpenIssueCommand.class, openIssueCommandHandler);
        this.commandDispatcher.registerHandler(CloseIssueCommand.class, closeIssueCommandHandler);
    }

    @Override
    public void dispatch(Object command) {
        this.commandDispatcher.dispatch(command);
    }
}
