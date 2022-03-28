package org.example.cqrs.command;

@FunctionalInterface
public interface CommandHandler<T> {
    void handle(T command);
}
