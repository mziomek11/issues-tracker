package org.example.issuestracker.issues.command.infrastructure.command;

import lombok.RequiredArgsConstructor;
import org.example.cqrs.command.dispatcher.CommandDispatcher;
import org.example.issuestracker.issues.command.application.command.*;
import org.example.issuestracker.issues.command.application.command.handler.*;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class IssueCommandRegistry {
    private final CommandDispatcher commandDispatcher;
    private final OpenIssueCommandHandler openIssueCommandHandler;
    private final CloseIssueCommandHandler closeIssueCommandHandler;
    private final RenameIssueCommandHandler renameIssueCommandHandler;
    private final ChangeIssueTypeCommandHandler changeIssueTypeCommandHandler;
    private final CommentIssueCommandHandler commentIssueCommandHandler;
    private final ChangeIssueCommentContentCommandHandler changeIssueCommentContentCommandHandler;
    private final HideIssueCommentCommandHandler hideIssueCommentCommandHandler;
    private final VoteIssueCommandHandler voteIssueCommandHandler;
    private final VoteIssueCommentCommandHandler voteIssueCommentCommandHandler;
    private final ChangeIssueContentCommandHandler changeIssueContentCommandHandler;

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(OpenIssueCommand.class, openIssueCommandHandler);
        commandDispatcher.registerHandler(CloseIssueCommand.class, closeIssueCommandHandler);
        commandDispatcher.registerHandler(RenameIssueCommand.class, renameIssueCommandHandler);
        commandDispatcher.registerHandler(ChangeIssueTypeCommand.class, changeIssueTypeCommandHandler);
        commandDispatcher.registerHandler(CommentIssueCommand.class, commentIssueCommandHandler);
        commandDispatcher.registerHandler(ChangeIssueCommentContentCommand.class, changeIssueCommentContentCommandHandler);
        commandDispatcher.registerHandler(HideIssueCommentCommand.class, hideIssueCommentCommandHandler);
        commandDispatcher.registerHandler(VoteIssueCommand.class, voteIssueCommandHandler);
        commandDispatcher.registerHandler(VoteIssueCommentCommand.class, voteIssueCommentCommandHandler);
        commandDispatcher.registerHandler(ChangeIssueContentCommand.class, changeIssueContentCommandHandler);
    }
}
