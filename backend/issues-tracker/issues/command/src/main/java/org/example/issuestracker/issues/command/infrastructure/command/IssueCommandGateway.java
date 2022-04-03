package org.example.issuestracker.issues.command.infrastructure.command;

import org.example.cqrs.command.CommandDispatcher;
import org.example.cqrs.command.CommandGateway;
import org.example.issuestracker.issues.command.application.command.*;
import org.example.issuestracker.issues.command.application.command.handler.*;

public class IssueCommandGateway implements CommandGateway {
    private final CommandDispatcher commandDispatcher;

    public IssueCommandGateway(
            CommandDispatcher commandDispatcher,
            OpenIssueCommandHandler openIssueCommandHandler,
            CloseIssueCommandHandler closeIssueCommandHandler,
            RenameIssueCommandHandler renameIssueCommandHandler,
            ChangeIssueTypeCommandHandler changeIssueTypeCommandHandler,
            CommentIssueCommandHandler commentIssueCommandHandler,
            ChangeIssueCommentContentCommandHandler changeIssueCommentContentCommandHandler,
            HideIssueCommentCommandHandler hideIssueCommentCommandHandler,
            VoteIssueCommandHandler voteIssueCommandHandler,
            VoteIssueCommentCommandHandler voteIssueCommentCommandHandler,
            ChangeIssueContentCommandHandler changeIssueContentCommandHandler
    ) {
        this.commandDispatcher = commandDispatcher;
        this.commandDispatcher.registerHandler(OpenIssueCommand.class, openIssueCommandHandler);
        this.commandDispatcher.registerHandler(CloseIssueCommand.class, closeIssueCommandHandler);
        this.commandDispatcher.registerHandler(RenameIssueCommand.class, renameIssueCommandHandler);
        this.commandDispatcher.registerHandler(ChangeIssueTypeCommand.class, changeIssueTypeCommandHandler);
        this.commandDispatcher.registerHandler(CommentIssueCommand.class, commentIssueCommandHandler);
        this.commandDispatcher.registerHandler(ChangeIssueCommentContentCommand.class, changeIssueCommentContentCommandHandler);
        this.commandDispatcher.registerHandler(HideIssueCommentCommand.class, hideIssueCommentCommandHandler);
        this.commandDispatcher.registerHandler(VoteIssueCommand.class, voteIssueCommandHandler);
        this.commandDispatcher.registerHandler(VoteIssueCommentCommand.class, voteIssueCommentCommandHandler);
        this.commandDispatcher.registerHandler(ChangeIssueContentCommand.class, changeIssueContentCommandHandler);
    }

    @Override
    public void dispatch(Object command) {
        this.commandDispatcher.dispatch(command);
    }
}
