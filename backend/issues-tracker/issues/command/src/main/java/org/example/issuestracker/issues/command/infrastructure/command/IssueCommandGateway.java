package org.example.issuestracker.issues.command.infrastructure.command;

import org.example.cqrs.command.CommandDispatcher;
import org.example.cqrs.command.CommandGateway;
import org.example.issuestracker.issues.command.command.OpenIssueCommand;
import org.example.issuestracker.issues.command.command.handler.OpenIssueCommandHandler;

public class IssueCommandGateway implements CommandGateway {
    private final CommandDispatcher commandDispatcher;

    public IssueCommandGateway(
            CommandDispatcher commandDispatcher,
            OpenIssueCommandHandler openIssueCommandHandler
    ) {
        this.commandDispatcher = commandDispatcher;
        this.commandDispatcher.registerHandler(OpenIssueCommand.class, openIssueCommandHandler);
    }

    @Override
    public void dispatch(Object command) {
        this.commandDispatcher.dispatch(command);
    }
}
