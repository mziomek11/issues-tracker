package org.example.cqrs.command;

public interface CommandDispatcher {
    <T> void registerHandler(Class<T> type, CommandHandler<T> handler);
    void dispatch(Object command);
}
