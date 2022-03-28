package org.example.issuestracker.issues.command.infrastructure;

import org.example.cqrs.infrastructure.CommandDispatcher;
import org.example.cqrs.infrastructure.CommandGateway;
import org.example.issuestracker.issues.command.command.OpenIssueCommand;
import org.example.issuestracker.issues.command.command.handler.OpenIssueCommandHandler;

public class IssuesCommandGateway implements CommandGateway {
    private final CommandDispatcher commandDispatcher;

    public IssuesCommandGateway(
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
