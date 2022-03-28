package org.example.cqrs.infrastructure;

import org.example.cqrs.command.CommandHandler;

public interface CommandDispatcher {
    <T> void registerHandler(Class<T> type, CommandHandler<T> handler);
    void dispatch(Object command);
}
