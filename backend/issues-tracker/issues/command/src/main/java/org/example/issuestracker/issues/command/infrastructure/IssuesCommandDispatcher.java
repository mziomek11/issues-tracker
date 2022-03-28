package org.example.issuestracker.issues.command.infrastructure;

import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.infrastructure.CommandDispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssuesCommandDispatcher implements CommandDispatcher {
    private final Map<Class, List<CommandHandler>> registry = new HashMap<>();

    @Override
    public <T> void registerHandler(Class<T> type, CommandHandler<T> handler) {
        var handlers = registry.computeIfAbsent(type, c -> new ArrayList<>());

        if (handlers.size() > 1) {
            throw new IllegalStateException("Command can have only one handler");
        }

        handlers.add(handler);
    }

    @Override
    public void dispatch(Object command) {
        var handlers = registry.get(command.getClass());

        handlers.get(0).handle(command);
    }
}
