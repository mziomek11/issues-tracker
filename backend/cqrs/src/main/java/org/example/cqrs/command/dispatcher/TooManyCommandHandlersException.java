package org.example.cqrs.command.dispatcher;

public class TooManyCommandHandlersException extends IllegalStateException {
    public TooManyCommandHandlersException() {
        super("Command can only have one handler.");
    }
}
