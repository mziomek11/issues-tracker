package org.example.issuestracker.issues.command.infrastructure.command;

import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.command.CommandDispatcher;
import org.example.cqrs.command.CommandHandlerNotFoundException;
import org.example.cqrs.command.TooManyCommandHandlersException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueCommandDispatcher implements CommandDispatcher {
    private final Map<Class<Object>, List<CommandHandler<Object>>> registry = new HashMap<>();

    @Override
    public <T> void registerHandler(Class<T> type, CommandHandler<T> handler) {
        var handlers = registry.computeIfAbsent((Class<Object>) type, c -> new ArrayList<>());

        if (handlers.size() > 1) {
            throw new TooManyCommandHandlersException();
        }

        handlers.add((CommandHandler<Object>) handler);
    }

    @Override
    public void dispatch(Object command) {
        var handlers = registry.get(command.getClass());

        if (handlers == null) {
            throw new CommandHandlerNotFoundException();
        }

        handlers.get(0).handle(command);
    }
}
