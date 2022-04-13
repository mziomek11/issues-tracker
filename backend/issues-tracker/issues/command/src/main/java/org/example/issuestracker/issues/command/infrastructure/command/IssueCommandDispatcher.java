package org.example.issuestracker.issues.command.infrastructure.command;

import org.example.cqrs.command.CommandHandler;
import org.example.cqrs.command.CommandDispatcher;
import org.example.cqrs.command.CommandHandlerNotFoundException;
import org.example.cqrs.command.TooManyCommandHandlersException;
import org.example.cqrs.domain.AggregateConcurrencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IssueCommandDispatcher implements CommandDispatcher {
    private final Map<Class<Object>, List<CommandHandler<Object>>> registry = new HashMap<>();

    Logger logger = LoggerFactory.getLogger(IssueCommandDispatcher.class);

    /**
     * @throws TooManyCommandHandlersException see {@link CommandDispatcher#registerHandler(Class, CommandHandler)}
     */
    @Override
    public <T> void registerHandler(Class<T> type, CommandHandler<T> handler) {
        var handlers = registry.computeIfAbsent((Class<Object>) type, c -> new ArrayList<>());

        if (handlers.size() > 1) {
            throw new TooManyCommandHandlersException();
        }

        handlers.add((CommandHandler<Object>) handler);
    }

    /**
     * @throws CommandHandlerNotFoundException see {@link CommandDispatcher#dispatch(Object)}
     */
    @Override
    public void dispatch(Object command) {
        var handlers = registry.get(command.getClass());

        if (handlers == null) {
            throw new CommandHandlerNotFoundException();
        }

        var commandDispatched = false;

        while (!commandDispatched) {
            try {
                handlers.get(0).handle(command);
                commandDispatched = true;
            } catch (AggregateConcurrencyException ex) {
                logger.info(String.format(
                        "AggregateConcurrencyException raised by %s for aggregate %s",
                        command.getClass().getName(),
                        ex.getAggregateId().toString()
                ));
            }
        }
    }
}
