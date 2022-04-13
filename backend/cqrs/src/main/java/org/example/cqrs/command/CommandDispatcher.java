package org.example.cqrs.command;

public interface CommandDispatcher {
    /**
     * @throws TooManyCommandHandlersException if given command has already registered handler
     */
    <T> void registerHandler(Class<T> type, CommandHandler<T> handler);
    /**
     * @throws CommandHandlerNotFoundException if given command has not registered handler
     */
    void dispatch(Object command);
}
